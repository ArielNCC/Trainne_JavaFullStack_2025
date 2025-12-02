package com.skillnest.cliente_rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI para el Sistema Catálogo
 * Genera documentación estándar sin dependencias de Swagger UI
 * La especificación está disponible en /v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema Catálogo - API REST")
                        .version("1.0.0")
                        .description("Sistema de gestión de catálogo de productos con interfaz web JSP y API REST. " +
                                   "Permite operaciones CRUD completas, búsqueda, gestión de stock e interfaz visual.")
                        .contact(new Contact()
                                .name("Nicolas Ariel")
                                .email("desarrollo@sistema-catalogo.com")
                                .url("https://sistema-catalogo.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.skillnest.com")
                                .description("Servidor de producción")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT para autenticación")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"));
    }
}