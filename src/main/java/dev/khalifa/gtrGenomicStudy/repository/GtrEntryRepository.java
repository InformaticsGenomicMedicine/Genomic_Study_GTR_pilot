package dev.khalifa.gtrGenomicStudy.repository;

import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface GtrEntryRepository extends ListCrudRepository<GtrEntry, Integer> {
    List<GtrEntry> findAllByGenesContains(String gene_key_word);

    Optional<List<GtrEntry>> findGtrEntriesByGenes(String gene);

    Optional<List<GtrEntry>>  findGtrEntriesByLabTestId(String labTestId);

    Optional<List<GtrEntry>>  findGtrEntriesByTestAccessionVerContaining(String testAccessionVer);
    List<GtrEntry> findGtrEntriesByConditionIdentifiersContains(String diseaseName);
}
