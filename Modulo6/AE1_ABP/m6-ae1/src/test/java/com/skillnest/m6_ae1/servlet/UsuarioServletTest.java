package com.skillnest.m6_ae1.servlet;

import com.skillnest.m6_ae1.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de UsuarioServlet usando Mockito")
class UsuarioServletTest {

    @InjectMocks
    private UsuarioServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    @BeforeEach
    void setUp() {
        lenient().when(request.getServletContext()).thenReturn(context);
        lenient().when(request.getContextPath()).thenReturn("");
    }

    @Test
    void testUsuarioValidoSeAgrega() throws Exception {
        when(request.getParameter("nombre")).thenReturn("Juan");
        when(request.getParameter("edad")).thenReturn("25");
        when(context.getAttribute("servletUsuarios")).thenReturn(null);

        servlet.doPost(request, response);

    @SuppressWarnings("unchecked")
    ArgumentCaptor<List<Usuario>> captor = ArgumentCaptor.forClass(List.class);
    verify(context).setAttribute(eq("servletUsuarios"), captor.capture());
    List<Usuario> usuarios = captor.getValue();
        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        assertEquals(25, usuarios.get(0).getEdad());
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testUsuarioInvalidoRedirige() throws Exception {
        when(request.getParameter("nombre")).thenReturn("");
        when(request.getParameter("edad")).thenReturn("-1");

        servlet.doPost(request, response);
        verify(response).sendRedirect(contains("/usuario/form"));
    }

    @Test
    void testEdadNoNumericaRedirige() throws Exception {
        when(request.getParameter("nombre")).thenReturn("Ana");
        when(request.getParameter("edad")).thenReturn("abc");

        servlet.doPost(request, response);
        verify(response).sendRedirect(contains("/usuario/form"));
    }
}
