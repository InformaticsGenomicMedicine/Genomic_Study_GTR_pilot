package dev.khalifa.gtrGenomicStudy.controller;

import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import dev.khalifa.gtrGenomicStudy.repository.GtrEntryRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
