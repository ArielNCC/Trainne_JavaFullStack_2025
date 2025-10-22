package com.skillnest.m6_ae1;

import com.skillnest.m6_ae1.servlet.UsuarioServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class M6Ae1Application {

	public static void main(String[] args) {
		SpringApplication.run(M6Ae1Application.class, args);
	}

	@Bean
	public ServletRegistrationBean<UsuarioServlet> usuarioServletRegistration() {
		ServletRegistrationBean<UsuarioServlet> registration = new ServletRegistrationBean<>(new UsuarioServlet());
		registration.addUrlMappings("/usuario/servlet");
		return registration;
	}

}
