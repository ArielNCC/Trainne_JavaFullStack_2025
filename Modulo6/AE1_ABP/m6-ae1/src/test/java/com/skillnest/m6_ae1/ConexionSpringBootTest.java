package com.skillnest.m6_ae1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Test de conexión y contexto Spring Boot")
class ConexionSpringBootTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void contextLoads() {
        assertNotNull(ctx);
        assertTrue(ctx.containsBeanDefinition("usuarioServletRegistration"));
    }
}
