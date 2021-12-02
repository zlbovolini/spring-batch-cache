package com.github.lbovolini.app.estabelecimento.compartilhado.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Estabelecimentos API")
                .description("API CRUD de estabelecimentos")
                .version("0.0.1")
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}