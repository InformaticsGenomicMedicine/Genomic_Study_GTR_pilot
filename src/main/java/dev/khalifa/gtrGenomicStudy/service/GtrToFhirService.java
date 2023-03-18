package dev.khalifa.gtrGenomicStudy.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import dev.khalifa.gtrGenomicStudy.model.Disease;
import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import dev.khalifa.gtrGenomicStudy.repository.DiseaseRepository;
import dev.khalifa.gtrGenomicStudy.repository.GtrEntryRepository;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r5.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
            identifierManufacturerTestName.setSystem("Manufacturer Test Name: https://www.ncbi.nlm.nih.gov/gtr/");
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
                    "Test Development Type: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Test_development.txt",
                    terms.testDevelopment.get(gtrEntry.get().testDevelopment()),
                    gtrEntry.get().testDevelopment()
            )));
        }

//        indicationTypes
        if (gtrEntry.get().indicationTypes() != null) {
            for (String term : gtrEntry.get().indicationTypes().split("\\|")) {
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "Indication Type: https://ftp.ncbi.nlm.nih.gov/pub/GTR/documentation/GTRFieldDefinitions.pdf",
                        terms.indicationType.get(term),
                        term
                )));
            }
        }

//        MethodCategories as Types
        if (gtrEntry.get().methodCategories() != null) {
            for (String term : gtrEntry.get().methodCategories().split("\\|")) {
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "Method Category: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Method_category.txt",
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
                        "Primary Test Methodology: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Primary_test_methodology.txt",
                        null,
                        term
                )))
                        .addDevice().setDevice(new Reference("https://build.fhir.org/device-example.html"));

                genomicStudy.getAnalysis().get(0).addInput().setFile(
                        new Reference("https://build.fhir.org/documentreference-example.html"));

            }

        }

//        Setting platform
//          This is not a real FHIR reference, but this was used to showcase the name of the device
//              as it may appear in a corresponding resource, i.e., device.displayName
        String noPlatform = "None/not applicable";
        if (gtrEntry.get().platforms() != null){
            if (!gtrEntry.get().platforms().equals(noPlatform)) {
                for (String term : gtrEntry.get().platforms().split("\\|")) {
                    genomicStudy.getAnalysis().get(0).addDevice().setDevice(new Reference(term));

                }
            }
        }

//        Referencing some example FHIR resources
        genomicStudy.setReferrer(new Reference("https://build.fhir.org/practitioner-example-f007-sh.html"));
        genomicStudy.setEncounter(new Reference("https://build.fhir.org/encounter-example.html"));
        genomicStudy.setDescription("Test for "
                + gtrEntry.get().labTestName()
                + " is available at: https://www.ncbi.nlm.nih.gov/gtr/tests/"
                + gtrEntry.get().labTestId());


        return genomicStudy;

    }


    public GenomicStudy getGenomicStudyByDisease(String diseaseConceptId) {
        List<Disease> diseaseList = diseaseRepository.findDiseasesByConceptId(diseaseConceptId);
        System.out.println(diseaseList);
        GenomicStudy genomicStudy = new GenomicStudy(
                new CodeableConcept(new Coding("http://hl7.org/fhir/genomicstudy-status",
                        "unknown", "Unknown")),
//                from https://build.fhir.org/patient-examples.html
                new Reference("https://build.fhir.org/patient-example.json")
        );

        //        setting identifiers
        List<Identifier> identifierList = new ArrayList<>();

        if(diseaseList.isEmpty()) {
            return genomicStudy;
        }
//          Setting identifiers based on disease concept_id
        Identifier identifierDiseaseId = new Identifier().setValue(diseaseConceptId);
        identifierDiseaseId.setSystem("https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt");
        identifierList.add(identifierDiseaseId);

        genomicStudy.setIdentifier(identifierList);
// Adding concept ID as a coding
        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.addCoding("https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt",
                diseaseList.get(0).conceptId(),
                diseaseList.get(0).diseaseName());

//            setting type
        HashSet<String> types = new HashSet<String>();
        HashSet<String> sources = new HashSet<String>();
        for (Disease disease: diseaseList){
            types.add(disease.category());

            sources.add(disease.sourceName());

            codeableConcept.addCoding(
                    disease.sourceName(),
                    disease.sourceId(),
                    disease.diseaseName()
            );


        }

        genomicStudy.addReason(new CodeableReference(codeableConcept));
//            System.out.println(types);
        for (String term: types){
            genomicStudy.addType(new CodeableConcept(new Coding(
                    "Disease Category: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt",
                    terms.diseaseCategory.get(term),
                    term
            )));
        }
//Setting description including a link to the condition page on NIH GTR
        genomicStudy.setDescription("# A genomic study for "
                + diseaseList.get(0).diseaseName()
                + ".  " +
                "## More details about this condition is available at: https://www.ncbi.nlm.nih.gov/gtr/conditions/"
                + diseaseConceptId +
                ".  " +
                "## MedGen record: https://www.ncbi.nlm.nih.gov/medgen/?term=" +
                diseaseConceptId
//                +"[source_id]"
        );


        HashSet<String> testDevelopmentTypes = new HashSet<String>();
        HashSet<String> indicationTypes = new HashSet<String>();
        HashSet<String> methodCategories = new HashSet<String>();
        HashSet<String> labTestIdList = new HashSet<String>();

        List<GtrEntry> gtrEntryList = repository.
                findGtrEntriesByConditionIdentifiersContains(diseaseList.get(0).diseaseName());
        if (gtrEntryList.isEmpty()){
            return genomicStudy;
        }
        for (GtrEntry gtrEntry : gtrEntryList){
            //      Setting types
//        testDevelopment
            if (gtrEntry.testDevelopment() != null) {
                testDevelopmentTypes.add(gtrEntry.testDevelopment());
            }
//         indicationTypes
            if (gtrEntry.indicationTypes() != null){
                for (String term : gtrEntry.indicationTypes().split("\\|")) {
                    indicationTypes.add(term);
                }
            }

//          methodCategories
            if (gtrEntry.methodCategories() != null){
                for (String term : gtrEntry.methodCategories().split("\\|")) {
                    methodCategories.add(term);
                }
            }

//        Adding analyses
/*            if (gtrEntry.methods() != null){
                for(String term : gtrEntry.methods().split("\\|")){
                    genomicStudy.addAnalysis().addMethodType(new CodeableConcept(new Coding(
                                    "Primary Test Methodology: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Primary_test_methodology.txt",
                                    null,
                                    term
                            )))
                            .addDevice().setDevice(new Reference("https://build.fhir.org/device-example.html"));

                    genomicStudy.getAnalysis().get(0).addInput().setFile(
                            new Reference("https://build.fhir.org/documentreference-example.html"));

                }

            }*/

//        Setting platform
//          This is not a real FHIR reference, but this was used to showcase the name of the device
//              as it may appear in a corresponding resource, i.e., device.displayName
/*            String noPlatform = "None/not applicable";
            if (gtrEntry.platforms() != null){
                if (!gtrEntry.platforms().equals(noPlatform)) {
                    for (String term : gtrEntry.platforms().split("\\|")) {
                        genomicStudy.getAnalysis().get(0).addDevice().setDevice(new Reference(term));

                    }
                }
            }*/
            //adding analysis level data
            if (!gtrEntry.methods().isEmpty()){
                System.out.println("number of GTR_Entries is: " + gtrEntryList.size());
                genomicStudy.addAnalysis()
                        .setTitle(gtrEntry.labTestName())
                        .setInstantiatesUri("https://www.ncbi.nlm.nih.gov/gtr/tests/" + gtrEntry.labTestId());
                //identifiers goes here
                //        setting identifiers
                List<Identifier> analysisIdentifierList = new ArrayList<>();
//          LabTestId
                Identifier identifierGtrId = new Identifier().setValue(gtrEntry.labTestId());
                identifierGtrId.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");

                analysisIdentifierList.add(identifierGtrId);

//        AccessionVersion
                Identifier identifierGtrAccessionVersion = new Identifier().setValue(gtrEntry.testAccessionVer());
                identifierGtrAccessionVersion.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
                analysisIdentifierList.add(identifierGtrAccessionVersion);

//        LabTestName
                Identifier identifierGtrLabTestName = new Identifier().setValue(gtrEntry.labTestName());
                identifierGtrLabTestName.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
                analysisIdentifierList.add(identifierGtrLabTestName);


//        manufacturerTestName
                if (gtrEntry.manufacturerTestName() != null) {
                    Identifier identifierManufacturerTestName = new Identifier().setValue(gtrEntry.manufacturerTestName());
                    identifierManufacturerTestName.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
                    analysisIdentifierList.add(identifierManufacturerTestName);
                }

//        labUniqueCode
                if (gtrEntry.labUniqueCode() != null) {
                    Identifier identifierLabUniqueCode = new Identifier().setValue(gtrEntry.labUniqueCode());
                    identifierLabUniqueCode.setSystem("https://www.ncbi.nlm.nih.gov/gtr/");
                    analysisIdentifierList.add(identifierLabUniqueCode);
                }

                genomicStudy.getAnalysis().get(genomicStudy.getAnalysis().size()-1).setIdentifier(analysisIdentifierList);
                //end of setting identifiers
//                Methods
//                System.out.println("Test Method: " + gtrEntry.methods());
                for (String term : gtrEntry.methods().split("\\|")){
                    System.out.println("term is: " + term);
       /*             genomicStudy.addAnalysis().addDevice().setDevice(new Reference(
                            "https://build.fhir.org/device-example.html"));*/

                    genomicStudy.getAnalysis()
                            .get(genomicStudy.getAnalysis().size()-1)
                            .addMethodType(new CodeableConcept(new Coding(
                            "Primary Test Methodology: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Primary_test_methodology.txt",
                            null,
                            term
                    )));
                }

                //        Setting platform
//          This is not a real FHIR reference, but this was used to showcase the name of the device
//              as it may appear in a corresponding resource, i.e., device.displayName
                String noPlatform = "None/not applicable";
                if (gtrEntry.platforms() != null){
                    if (!gtrEntry.platforms().equals(noPlatform)) {
                        for (String term : gtrEntry.platforms().split("\\|")) {
                            genomicStudy.getAnalysis()
                                    .get(genomicStudy.getAnalysis().size()-1)
                                    .addDevice().setDevice(new Reference(term));

                        }
                    }
                }
            }
        } //End of GtrEntry Loop

        if (!testDevelopmentTypes.isEmpty()){
            for (String term: testDevelopmentTypes){
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "Test Development Type: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Test_development.txt",
                        terms.testDevelopment.get(term),
                        term
                )));
            }
        }

        if (!indicationTypes.isEmpty()){
            for (String term: indicationTypes){
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "Indication Type: https://ftp.ncbi.nlm.nih.gov/pub/GTR/documentation/GTRFieldDefinitions.pdf",
                        terms.indicationType.get(term),
                        term
                )));
            }
        }

        if (!methodCategories.isEmpty()){
            for (String term: methodCategories){
                genomicStudy.addType(new CodeableConcept(new Coding(
                        "Method Category: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/Method_category.txt",
                        null,
                        term
                )));
            }
        }


        return genomicStudy;
    }
}
