package com.ejercicio.utilidades;

/**
 * Métodos estáticos para operaciones matemáticas básicas.
 */
public class UtilidadesMatematicas {

    /**
     * @return true si el número es par, false en caso contrario
     */
    public static boolean esPar(int numero) {
        return numero % 2 == 0;
    }

    /**
     * Calcula el factorial de un número no negativo.
     * @throws IllegalArgumentException si numero < 0
     */
    public static long obtenerFactorial(int numero) {
        if (numero < 0) {
            throw new IllegalArgumentException("El número debe ser >= 0");
        }
        long resultado = 1;
        for (int i = 2; i <= numero; i++) {
            resultado *= i;
        }
        return resultado;
    }

    /**
     * @return true si el número es primo (>1), false en caso contrario
     */
    public static boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        int limite = (int) Math.sqrt(numero);
        for (int i = 2; i <= limite; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calcula el máximo común divisor (MCD) de a y b usando el algoritmo de Euclides.
     * @throws IllegalArgumentException si cualquiera es negativo
     */
    public static int obtenerMCD(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Ambos valores deben ser >= 0");
        }
        while (b != 0) {
            int tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }
}
