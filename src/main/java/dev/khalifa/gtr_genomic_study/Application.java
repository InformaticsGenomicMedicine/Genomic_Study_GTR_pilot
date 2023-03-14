package dev.khalifa.gtr_genomic_study;

import dev.khalifa.gtr_genomic_study.config.GtrGenomicStudyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(GtrGenomicStudyProperties.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
