package com.skillnest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;


/**
 * Clase principal de la aplicación Biblioteca-skillnest
 * Sistema de gestión de inventario para biblioteca
 * @author SkillNest Team
 */
@SpringBootApplication
@Controller
public class BibliotecaSkillnestApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BibliotecaSkillnestApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaSkillnestApplication.class, args);
		System.out.println("==============================================");
		System.out.println("  Biblioteca-skillnest - Sistema iniciado");
		System.out.println("  http://localhost:8081");
		System.out.println("==============================================");
	} 
	
}
