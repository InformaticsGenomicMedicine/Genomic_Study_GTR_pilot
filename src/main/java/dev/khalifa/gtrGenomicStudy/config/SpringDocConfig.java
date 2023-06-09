package dev.khalifa.gtrGenomicStudy.config;

import dev.khalifa.gtrGenomicStudy.util.ReadJsonFileToJsonObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.io.IOException;

@Configuration
@OpenAPIDefinition
public class SpringDocConfig {
    @Bean
    public OpenAPI baseOpenAPI() throws IOException {
        ReadJsonFileToJsonObject readJsonFileToJsonObject = new ReadJsonFileToJsonObject();
        ApiResponse badRequestAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(
                                "default",
                                new Example().value(
//                                        "{\"code\":400, \"Status\":\"Bad Request!\", \"Message\":\"Bad Request!\"}"
                                        readJsonFileToJsonObject.read().get("badRequestResponse").toString()
                                )
                        ))
        ).description("Bad Request!");

        ApiResponse internalServerErrorAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(
                                "default",
                                new Example().value(
//                                        "{\"code\":500, \"Status\":\"Internal Server Error!\", \"Message\":\"Internal Server Error!\"}"
                                        readJsonFileToJsonObject.read().get("internalServerErrorResponse").toString()
                                )
                        ))
        ).description("Internal Server Error!");

        ApiResponse successAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples(
                                "default",
                                new Example().value(
                                        readJsonFileToJsonObject.read().get("success").toString()
                                )
                        ))
        ).description("Internal Server Error!");

        Components components = new Components();
        components.addResponses("badRequestAPI", badRequestAPI);
        components.addResponses("internalServerErrorAPI", internalServerErrorAPI);
        components.addResponses("success", successAPI);
        return new OpenAPI()
                .components(components)
                .info(new Info().title("Spring Doc of GTR record as FHIR Genomic Study")
                .version("1.0.0").description("""
                                This is a documentation of how to use this tool to get a NCBI-GTR Record as a FHIR R5 Genomic Study Resource.
                                This tool provide More APIs to check original data of specific records.
                                Internal Ids ranges from 1-417356
                                """));
    }
}
