package dev.khalifa.gtrGenomicStudy.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import dev.khalifa.gtrGenomicStudy.service.GtrToFhirService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.hl7.fhir.r5.model.GenomicStudy;
import org.hl7.fhir.r5.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gtr_fhir")
@CrossOrigin // To Avoid CORS problems
public class GtrFhirController {
    private final GtrToFhirService gtrToFhirService;

//    private FhirContext ctx;
    private IParser parser;

    public GtrFhirController(GtrToFhirService gtrToFhirService) {
        this.gtrToFhirService = gtrToFhirService;
        this.parser = gtrToFhirService.ctx.newJsonParser();
    }

    @GetMapping(value = "/{internalId}", produces = "application/json")
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
        return parser.encodeResourceToString(gtrToFhirService.getGenomicStudy(internalId));
    }
}
