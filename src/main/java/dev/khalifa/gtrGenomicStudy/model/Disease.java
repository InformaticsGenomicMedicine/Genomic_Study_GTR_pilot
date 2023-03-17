package dev.khalifa.gtrGenomicStudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Disease(
        @Id
        Integer id,
        String diseaseName,
        String sourceName,
        String conceptID,
        String sourceID,
        String diseaseMIM,
        String lastModified,
        String category
) {
}
