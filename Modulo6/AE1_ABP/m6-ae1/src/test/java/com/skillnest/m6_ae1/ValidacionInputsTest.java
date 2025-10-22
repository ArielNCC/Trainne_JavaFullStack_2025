package com.skillnest.m6_ae1;

import com.skillnest.m6_ae1.model.Usuario;
import com.skillnest.m6_ae1.utils.ValidacionInputs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de ValidacionInputs")
public class ValidacionInputsTest {

    @Nested
    @DisplayName("Validación de nombre")
    class NombreTests {
        @Test
        void nombreValido() {
            assertTrue(ValidacionInputs.esNombreValido("Juan"));
        }

        @Test
        void nombreNulo() {
            assertFalse(ValidacionInputs.esNombreValido(null));
        }

        @Test
        void nombreVacio() {
            assertFalse(ValidacionInputs.esNombreValido("   "));
        }
    }

    @Nested
    @DisplayName("Validación de edad")
    class EdadTests {
        @Test
        void edadValida() {
            assertTrue(ValidacionInputs.esEdadValida(20));
        }

        @Test
        void edadNula() {
            assertFalse(ValidacionInputs.esEdadValida(null));
        }

        @Test
        void edadNegativa() {
            assertFalse(ValidacionInputs.esEdadValida(-1));
        }
    }

    @Nested
    @DisplayName("Validación de usuario completo")
    class UsuarioTests {
        @Test
        void usuarioValido() {
            Usuario u = new Usuario("Ana", 30);
            assertTrue(ValidacionInputs.validarUsuario(u));
        }

        @Test
        void usuarioNombreInvalido() {
            Usuario u = new Usuario("", 25);
            assertFalse(ValidacionInputs.validarUsuario(u));
        }

        @Test
        void usuarioEdadInvalida() {
            Usuario u = new Usuario("Pedro", -5);
            assertFalse(ValidacionInputs.validarUsuario(u));
        }

        @Test
        void usuarioNulo() {
            assertFalse(ValidacionInputs.validarUsuario(null));
        }
    }
}
