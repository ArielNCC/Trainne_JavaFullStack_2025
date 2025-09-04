package com.ejercicio.utilidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UtilidadesMatematicas.
 */
public class UtilidadesMatematicasTest {

    private int numeroPar;
    private int numeroImpar;
    private int numeroPrimo;
    private int numeroCero;
    private int numeroNegativo;

    @BeforeEach // -> Fixture
    void setUp() {
        numeroPar    = 10;
        numeroImpar  = 15;
        numeroPrimo  = 13;
        numeroCero   = 0;
        numeroNegativo = -5;
    }

    @Test
    void testEsPar_conNumerosValidos() {
        assertTrue(UtilidadesMatematicas.esPar(numeroPar));
        assertFalse(UtilidadesMatematicas.esPar(numeroImpar));
        assertTrue(UtilidadesMatematicas.esPar(numeroCero));
    }

    @Test
    void testObtenerFactorial_validoYCero() {
        assertEquals(120, UtilidadesMatematicas.obtenerFactorial(5));
        assertEquals(1, UtilidadesMatematicas.obtenerFactorial(numeroCero));
    }

    @Test
    void testObtenerFactorial_numeroNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> UtilidadesMatematicas.obtenerFactorial(numeroNegativo));
    }

    @Test
    void testEsPrimo_variosCasos() {
        assertTrue(UtilidadesMatematicas.esPrimo(numeroPrimo));
        assertFalse(UtilidadesMatematicas.esPrimo(1));
        assertFalse(UtilidadesMatematicas.esPrimo(0));
        assertFalse(UtilidadesMatematicas.esPrimo(12));
    }

    @Test
    void testObtenerMCD_valido() {
        assertEquals(6, UtilidadesMatematicas.obtenerMCD(54, 24));
        assertEquals(1, UtilidadesMatematicas.obtenerMCD(17, 31));
    }

    @Test
    void testObtenerMCD_conCero() {
        assertEquals(5, UtilidadesMatematicas.obtenerMCD(0, 5));
        assertEquals(7, UtilidadesMatematicas.obtenerMCD(7, 0));
    }

    @Test
    void testObtenerMCD_negativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> UtilidadesMatematicas.obtenerMCD(numeroNegativo, numeroPar));
    }
}
