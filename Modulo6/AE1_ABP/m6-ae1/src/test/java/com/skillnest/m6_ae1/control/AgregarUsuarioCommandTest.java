package com.skillnest.m6_ae1.control;

import com.skillnest.m6_ae1.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de AgregarUsuarioCommand usando Mockito")
class AgregarUsuarioCommandTest {
    @Mock
    private ServletContext servletContext;

    @InjectMocks
    private AgregarUsuarioCommand command;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    void testRootRedirect() {
        String result = command.root();
        assertEquals("redirect:/usuario/form", result);
    }

    @Test
    void testMostrarFormulario() {
        String result = command.mostrarFormulario(model);
        assertEquals("form", result);
        verify(model).addAttribute(eq("usuario"), any(Usuario.class));
    }

    @Test
    void testVerUsuariosConUsuarios() {
        List<Usuario> usuarios = List.of(new Usuario("Juan", 20), new Usuario("Ana", 30));
        when(servletContext.getAttribute("servletUsuarios")).thenReturn(usuarios);
        String result = command.verUsuarios(model);
        assertEquals("usuarios", result);
        verify(model).addAttribute(eq("usuarios"), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void testVerUsuariosSinUsuarios() {
        when(servletContext.getAttribute("servletUsuarios")).thenReturn(null);
        String result = command.verUsuarios(model);
        assertEquals("usuarios", result);
        verify(model).addAttribute(eq("usuarios"), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void testAgregarUsuarioValido() {
        Usuario usuario = new Usuario("Juan", 25);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servletContext.getAttribute("servletUsuarios")).thenReturn(null);
        
        String result = command.agregarUsuario(usuario, bindingResult, request, redirectAttributes, model);
        
        assertEquals("redirect:/usuario/ver", result);
        verify(redirectAttributes).addFlashAttribute("mensaje", "Usuario agregado exitosamente");
    }

    @Test
    void testAgregarUsuarioConErrores() {
        Usuario usuario = new Usuario("", -1);
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String result = command.agregarUsuario(usuario, bindingResult, request, redirectAttributes, model);
        
        assertEquals("form", result);
    }
}
