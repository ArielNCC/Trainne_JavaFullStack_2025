package com.skillnest.producto_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.skillnest.producto_mvc")
public class ProductoMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoMvcApplication.class, args);
	}

}
