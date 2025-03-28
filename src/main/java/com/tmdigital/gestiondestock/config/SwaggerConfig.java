package com.tmdigital.gestiondestock.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info().title("GStock API")
                    .description("Company stock management API")
                    .version("v0.0.1")
            )
            .components(new Components().addSecuritySchemes(
                "bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            ))
            .addSecurityItem(
                new SecurityRequirement().addList("bearer-key")
            )
        ;
    }

    @Bean
    public GroupedOpenApi companiesApi() {
        return GroupedOpenApi.builder()
            .group("gstock-companies")
            .pathsToMatch("/**")
            .build();
    }
}