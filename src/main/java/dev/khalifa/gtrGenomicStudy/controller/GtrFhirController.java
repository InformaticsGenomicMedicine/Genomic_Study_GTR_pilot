package dev.khalifa.gtrGenomicStudy.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gtr_fhir")
@CrossOrigin // To Avoid CORS problems
public class GtrFhirController {

    public FhirContext ctx = FhirContext.forR5();
    IParser parser = ctx.newJsonParser();

    private String serverBase = "http://localhost:8080/fhir/";
    // setting HAPI FHIR client to use specific server
//    TODO using dynamic server base url from the app properties
    IGenericClient client = ctx.newRestfulGenericClient(serverBase);

    @GetMapping("/{internalId}")
//    The following is a swagger annotation
    @Operation(description = "A Genomic Study resource for specific NCBI-GTR entry",
    responses = {
            @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully served the FHIR resource",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"response\":\"TBA\"}"
                                    )
                            }
                    )
            )
    })
    String gtrAsGenomicResource(@PathVariable String internalId){
//        return repository.findGtrEntriesByTestAccessionVerContaining(testAccessionVer);
        return """
                { "Entry number": "1"}
                """ ;
    }
}
