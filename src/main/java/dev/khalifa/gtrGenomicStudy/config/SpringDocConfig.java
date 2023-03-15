package dev.khalifa.gtrGenomicStudy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SpringDocConfig {
    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(new Info().title("Spring Doc ++!!++")
                .version("1.0.0").description("TBA: This is the doc PLACEHOLDER!"));
    }
}
