package dev.khalifa.gtrGenomicStudy.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r5.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GtrToFhirService {

    public FhirContext ctx = FhirContext.forR5();
    IParser parser = ctx.newJsonParser();
    @Value("${FHIR.serverBase.url}")
    private String serverBase;
    // setting HAPI FHIR client to use specific server
//    TODO using dynamic server base url from the app properties
    IGenericClient client = ctx.newRestfulGenericClient("http://roffhi101a.mayo.edu:8083/fhir");
    GenomicStudy genomicStudy = new GenomicStudy(
            new CodeableConcept(new Coding("http://hl7.org/fhir/genomicstudy-status", "unknown", "Unknown")), new Reference(" http://roffhi101a.mayo.edu:8083/fhir/Patient/1")
    );

    public GenomicStudy getGenomicStudy(String internalId){
        return genomicStudy;
    }


}
