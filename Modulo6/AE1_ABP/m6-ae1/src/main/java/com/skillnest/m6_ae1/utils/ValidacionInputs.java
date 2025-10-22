package com.skillnest.m6_ae1.utils;

import com.skillnest.m6_ae1.model.Usuario;

public class ValidacionInputs {

	public static boolean esNombreValido(String nombre) {
		return nombre != null && !nombre.trim().isEmpty();
	}

	public static boolean esEdadValida(Integer edad) {
		return edad != null && edad >= 0;
	}

	public static boolean validarUsuario(Usuario usuario) {
		if (usuario == null) return false;
		return esNombreValido(usuario.getNombre()) && esEdadValida(usuario.getEdad());
	}

}
