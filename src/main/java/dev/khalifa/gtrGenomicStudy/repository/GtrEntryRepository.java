package dev.khalifa.gtrGenomicStudy.repository;

import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface GtrEntryRepository extends ListCrudRepository<GtrEntry, Integer> {
    List<GtrEntry> findAllByGenesContains(String keyword);
}
