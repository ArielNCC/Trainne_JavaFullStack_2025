package com.skillnest.cliente_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración global de CORS para el Sistema Catálogo
 * Permite el acceso desde otros dominios tanto a la interfaz JSP como a la API REST
 * Facilita la interoperabilidad entre distintas aplicaciones cliente
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir todos los orígenes para máxima interoperabilidad
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Permitir todos los métodos HTTP necesarios para REST
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"
        ));
        
        // Permitir todos los headers necesarios
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir credenciales (cookies, authorization headers)
        configuration.setAllowCredentials(true);
        
        // Exponer headers que el cliente puede leer
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "Cache-Control"
        ));
        
        // Tiempo de cache para preflight requests
        configuration.setMaxAge(3600L);
        
        // Aplicar configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}