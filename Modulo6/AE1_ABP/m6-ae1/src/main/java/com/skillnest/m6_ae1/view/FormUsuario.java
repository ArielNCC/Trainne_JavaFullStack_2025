package com.skillnest.m6_ae1.view;

public class FormUsuario {

	private String nombre;
	private Integer edad;

	public FormUsuario() {
	}

	public FormUsuario(String nombre, Integer edad) {
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
