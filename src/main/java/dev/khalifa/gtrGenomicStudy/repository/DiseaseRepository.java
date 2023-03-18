package dev.khalifa.gtrGenomicStudy.repository;

import dev.khalifa.gtrGenomicStudy.model.Disease;
import dev.khalifa.gtrGenomicStudy.model.GtrEntry;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface DiseaseRepository extends ListCrudRepository<Disease, Integer> {
    List<Disease> findDiseaseByDiseaseName(String diseaseName);
    List<Disease> findDiseasesByConceptId(String diseaseConceptId);

}
