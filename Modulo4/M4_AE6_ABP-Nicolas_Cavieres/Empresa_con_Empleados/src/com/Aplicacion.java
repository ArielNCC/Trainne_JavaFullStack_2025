package com;

import com.clases.Empleado;
import com.clases.Gerente;
import com.clases.Director;

public class Aplicacion {
    public static void main(String[] args) {
        Empleado empleado = new Empleado("Ana Torres", 101, 5000);
        Gerente gerente = new Gerente("Carlos Rivas", 102, 5000, 500000);
        Director director = new Director("María Gómez", 103, 5000, 1500000, 100, 3000);

        empleado.mostrarInformacion();
        gerente.mostrarInformacion();
        director.mostrarInformacion();
    }
}
