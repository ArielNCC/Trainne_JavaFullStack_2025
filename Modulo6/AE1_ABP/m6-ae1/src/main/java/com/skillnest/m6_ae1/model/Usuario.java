package com.skillnest.m6_ae1.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Usuario {

	@NotBlank(message = "El nombre no puede estar vacío")
	private String nombre;

	@NotNull(message = "La edad es obligatoria")
	@Min(value = 0, message = "La edad debe ser un número positivo")
	private Integer edad;

	public Usuario() {
	}

	public Usuario(String nombre, Integer edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

}
