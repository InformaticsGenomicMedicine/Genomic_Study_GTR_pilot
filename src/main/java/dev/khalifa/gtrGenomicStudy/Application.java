package dev.khalifa.gtrGenomicStudy;

import dev.khalifa.gtrGenomicStudy.config.GtrGenomicStudyProperties;
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
