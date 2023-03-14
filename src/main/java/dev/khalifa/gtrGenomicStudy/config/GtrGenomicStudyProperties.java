package dev.khalifa.gtrGenomicStudy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "gs")
public record GtrGenomicStudyProperties(String fhirVersion, String author, String organization) {
}
