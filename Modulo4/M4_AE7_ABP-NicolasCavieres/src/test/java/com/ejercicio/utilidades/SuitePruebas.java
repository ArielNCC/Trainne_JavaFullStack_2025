package com.ejercicio.utilidades;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite para agrupar todas las clases de tests de Utilidades.
 */
@Suite
@SelectClasses({
        UtilidadesMatematicasTest.class
        // en el futuro añade aquí nuevas clases de test
})
public class SuitePruebas {
}
