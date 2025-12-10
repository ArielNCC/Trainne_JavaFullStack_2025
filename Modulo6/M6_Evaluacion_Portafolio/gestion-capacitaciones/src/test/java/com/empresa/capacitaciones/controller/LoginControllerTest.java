package com.empresa.capacitaciones.controller;

import com.empresa.capacitaciones.config.SecurityConfig;
import com.empresa.capacitaciones.security.CustomUserDetailsService;
import com.empresa.capacitaciones.security.UserEntity;
import com.empresa.capacitaciones.security.UserRepository;
import com.empresa.capacitaciones.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmpleadoService empleadoService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService; // Required for Spring Security

    @Test
    public void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"EMPLEADO"})
    public void shouldRedirectToPanelWhenAuthenticated() throws Exception {
        // This tests the /panel logic indirectly or we can test /panel directly
        mockMvc.perform(get("/panel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/empleado/cursos"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldRedirectToAdminCursosWhenAdmin() throws Exception {
        mockMvc.perform(get("/panel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/cursos"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void shouldReturnPerfilPage() throws Exception {
        UserEntity user = new UserEntity("testuser", "password", "test@example.com");
        given(userRepository.findByUsername("testuser")).willReturn(Optional.of(user));

        mockMvc.perform(get("/perfil"))
                .andExpect(status().isOk())
                .andExpect(view().name("perfil"))
                .andExpect(model().attributeExists("usuario"));
    }
}
