package com.skillnest.formulario_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class FormularioDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormularioDemoApplication.class, args);
	}

}
