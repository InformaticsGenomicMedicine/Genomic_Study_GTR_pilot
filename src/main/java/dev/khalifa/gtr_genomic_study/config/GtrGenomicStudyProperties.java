package dev.khalifa.gtr_genomic_study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "gs")
public record GtrGenomicStudyProperties(String fhirVersion, String author, String organization) {
}
