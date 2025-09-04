package com.clases;

public class Empleado {
    protected String nombre;
    protected int id;
    protected double salarioBase;

    public Empleado(String nombre, int id, double salarioBase) {
        this.nombre = nombre;
        this.id = id;
        this.salarioBase = salarioBase;
    }

    public double calcularSalarioTotal() {
        return salarioBase * 160;  // 160 horas al mes
    }

    public void mostrarInformacion() {
        System.out.println("Empleado:");
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Salario Base: $" + salarioBase);
        System.out.println("Salario Total: $" + calcularSalarioTotal());
        System.out.println();
    }
}
