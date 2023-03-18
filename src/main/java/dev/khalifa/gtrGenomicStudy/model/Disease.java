package dev.khalifa.gtrGenomicStudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Disease(
        @Id
        Integer id,
        @Column(value = "disease_name")
        String diseaseName,
        @Column(value = "source_name")
        String sourceName,
        @Column(value = "concept_id")
        String conceptId,
        @Column(value = "source_id")
        String sourceId,
        @Column(value = "disease_mim")
        String diseaseMim,
        @Column(value = "last_modified")
        String lastModified,
        String category
) {
}
