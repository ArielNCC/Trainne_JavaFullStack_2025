package com.clases;

public class Gerente extends Empleado {
    protected double bono;

    public Gerente(String nombre, int id, double salarioBase, double bono) {
        super(nombre, id, salarioBase);
        this.bono = bono;
    }

    @Override
    public double calcularSalarioTotal() {
        return salarioBase + bono;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Gerente:");
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Salario Base: $" + salarioBase);
        System.out.println("Bono: $" + bono);
        System.out.println("Salario Total: $" + calcularSalarioTotal());
        System.out.println();
    }
}

