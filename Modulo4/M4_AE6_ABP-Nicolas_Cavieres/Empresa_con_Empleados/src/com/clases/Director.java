package com.clases;

public class Director extends Gerente {
    private double acciones;
    private double valor_acciones;

    public Director(String nombre, int id, double salarioBase, double bono, double acciones, double valor_acciones) {
        super(nombre, id, salarioBase, bono);
        this.acciones = acciones;
        this.valor_acciones = valor_acciones;
    }

    @Override
    public double calcularSalarioTotal() {
        return salarioBase + bono + (acciones*valor_acciones);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Director:");
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Salario Base: $" + salarioBase);
        System.out.println("Bono: $" + bono);
        System.out.println("Acciones: $" + acciones);
        System.out.println("Valor de Acciones: $" + valor_acciones);
        System.out.println("Salario Total: $" + calcularSalarioTotal());
        System.out.println();
    }
}

