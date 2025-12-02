package com.skillnest.cliente_rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClienteRestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClienteRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Demostración automática deshabilitada
		// La aplicación ahora se inicia normalmente y muestra la página de login
		System.out.println("✅ Aplicación iniciada correctamente");
		System.out.println("🌐 Accede a: http://localhost:8081");
		System.out.println("👤 Usuarios: admin/admin123 o usuario1/usuario123");
	}
}