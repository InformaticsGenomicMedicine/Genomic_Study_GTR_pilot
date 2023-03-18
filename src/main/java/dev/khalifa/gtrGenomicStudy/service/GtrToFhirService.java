package dev.khalifa.gtrGenomicStudy.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import dev.khalifa.gtrGenomicStudy.model.Disease;
import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import dev.khalifa.gtrGenomicStudy.repository.DiseaseRepository;
import dev.khalifa.gtrGenomicStudy.repository.GtrEntryRepository;
import org.hl7.fhir.r5.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GtrToFhirService {

    private final GtrEntryRepository repository;
    private final DiseaseRepository diseaseRepository;
    private final StandardTerms terms;

    public FhirContext ctx = FhirContext.forR5();
    IParser parser = ctx.newJsonParser();
    @Value("${FHIR.serverBase.url}")
    private String serverBase;
    // setting HAPI FHIR client to use specific server
//    TODO using dynamic server base url from the app properties
    IGenericClient client = ctx.newRestfulGenericClient("http://roffhi101a.mayo.edu:8083/fhir");

    public GtrToFhirService(GtrEntryRepository repository, DiseaseRepository diseaseRepository, StandardTerms terms) {
        this.repository = repository;
        this.diseaseRepository = diseaseRepository;
        this.terms = terms;
    }

    public GenomicStudy getGenomicStudy(String internalId){

        Optional<GtrEntry> gtrEntry = repository.findById(Integer.valueOf(internalId));

        GenomicStudy genomicStudy = new GenomicStudy(
                new CodeableConcept(new Coding("http://hl7.org/fhir/genomicstudy-status",
                        "unknown", "Unknown")),
//                from https://build.fhir.org/patient-examples.html
                new Reference("https://build.fhir.org/patient-example.json")
        );

//        setting identifiers
        List<Identifier> identifierList = new ArrayList<>();
//          LabTestId
        Identifier identifierGtrId = new Identifier().setValue(gtrEntry.get().labTestId());
        identifierGtrId.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");

        identifierList.add(identifierGtrId);

//        AccessionVersion
        Identifier identifierGtrAccessionVersion = new Identifier().setValue(gtrEntry.get().testAccessionVer());
        identifierGtrAccessionVersion.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
        identifierList.add(identifierGtrAccessionVersion);

//        LabTestName
        Identifier identifierGtrLabTestName = new Identifier().setValue(gtrEntry.get().labTestName());
        identifierGtrLabTestName.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
        identifierList.add(identifierGtrLabTestName);


//        manufacturerTestName
        if (gtrEntry.get().manufacturerTestName() != null) {
            Identifier identifierManufacturerTestName = new Identifier().setValue(gtrEntry.get().manufacturerTestName());
            identifierManufacturerTestName.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
            identifierList.add(identifierManufacturerTestName);
        }

//        labUniqueCode
        if (gtrEntry.get().labUniqueCode() != null) {
            Identifier identifierLabUniqueCode = new Identifier().setValue(gtrEntry.get().labUniqueCode());
            identifierLabUniqueCode.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
            identifierList.add(identifierLabUniqueCode);
        }


        genomicStudy.setIdentifier(identifierList);


//      Setting types
//        testDevelopment
        if (gtrEntry.get().testDevelopment() != null) {
            genomicStudy.addType(new CodeableConcept(new Coding(
                    "https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Test_development.txt",
                    terms.testDevelopment.get(gtrEntry.get().testDevelopment()),
                    gtrEntry.get().testDevelopment()
            )));
        }

//        indicationTypes
        if (gtrEntry.get().indicationTypes() != null) {
            for (String term : gtrEntry.get().indicationTypes().split("\\|")) {
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "https://ftp.ncbi.nlm.nih.gov/pub/GTR/documentation/GTRFieldDefinitions.pdf",
                        terms.indicationType.get(term),
                        term
                )));
            }
        }

//        MethodCategories as Types
        if (gtrEntry.get().methodCategories() != null) {
            for (String term : gtrEntry.get().methodCategories().split("\\|")) {
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Method_category.txt",
                        null,
                        term
                )));
            }
        }



//        Setting reasons
        if (gtrEntry.get().conditionIdentifiers() != null) {
            for (String term : gtrEntry.get().conditionIdentifiers().split("\\|")) {
//                System.out.println("Terms: " + term + "\n");
                List<Disease> diseaseIdentifierList = diseaseRepository.findDiseaseByDiseaseName(term);
                if (!diseaseIdentifierList.isEmpty()){
//                    System.out.println("diseaseIdentifierList: "+diseaseIdentifierList);
                    for (Disease disease : diseaseIdentifierList){
                        CodeableConcept codeableConcept = new CodeableConcept();
                        codeableConcept.addCoding("https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt",
                                disease.conceptId(),
                                disease.diseaseName());
                        codeableConcept.addCoding(
                                disease.sourceName(),
                                disease.sourceId(),
                                disease.diseaseName()
                        );
                        genomicStudy.addReason(new CodeableReference(codeableConcept));
                    }
                }
            }
        }

//        Adding analyses
        if (gtrEntry.get().methods() != null){
            for(String term : gtrEntry.get().methods().split("\\|")){
                genomicStudy.addAnalysis().addMethodType(new CodeableConcept(new Coding(
                        "https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Primary_test_methodology.txt",
                        null,
                        term
                )));

            }

        }

        return genomicStudy;

    }


}
