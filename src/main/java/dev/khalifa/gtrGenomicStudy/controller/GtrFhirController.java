package dev.khalifa.gtrGenomicStudy.controller;

import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gtr_fhir")
@CrossOrigin // To Avoid CORS problems
public class GtrFhirController {
    @GetMapping("/{internalId}")
    String gtrAsGenomicResource(@PathVariable String internalId){
//        return repository.findGtrEntriesByTestAccessionVerContaining(testAccessionVer);
        return "Yes, E7L, this is the FHIR starter on built of Entry number: " + internalId;
    }
}
