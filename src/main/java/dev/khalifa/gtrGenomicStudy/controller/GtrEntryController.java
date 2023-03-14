package dev.khalifa.gtrGenomicStudy.controller;

import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import dev.khalifa.gtrGenomicStudy.repository.GtrEntryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gtr_entry")
@CrossOrigin // To Avoid CORS problems
public class GtrEntryController {
    private final GtrEntryRepository repository;

    public GtrEntryController(GtrEntryRepository repository) {
        this.repository = repository;
    }

    //    make a request and find all GTR entries stored in the database
    @GetMapping("")
    public List<GtrEntry> findAll(){
        return repository.findAll();
    }

    @GetMapping ("/{testAccessionVer}")
    Optional<List<GtrEntry>>  findByTestAccessionVer(@PathVariable String testAccessionVer){
//        the orElseThrow is a good example of how to handle unexpected requests
        return repository.findGtrEntriesByTestAccessionVerContaining(testAccessionVer);
    }

//    To retrieve specific entry by our internal ID
    @GetMapping ("/internal_id/{internal_id}")
    GtrEntry findById(@PathVariable Integer internal_id){
//        the orElseThrow is a good example of how to handle unexpected requests
        return repository.findById(internal_id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "ohhh, it seems what you are looking for is not there yet!!"));
    }

    @GetMapping ("/genes/{gene}")
    List<GtrEntry> findByGene(@PathVariable String gene){
        return repository.findAllByGenesContains(gene);
    }

    @GetMapping ("/lab_test_id/{labTestId}")
    Optional<List<GtrEntry>>  findByLabTestId(@PathVariable String labTestId){
        return repository.findGtrEntriesByLabTestId(labTestId);
    }
}
