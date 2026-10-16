package com.docflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI docFlowOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("docFlow API")
                .version("v1")
                .description("Company asset search API. Use Try it out to test endpoints."));
    }
}
