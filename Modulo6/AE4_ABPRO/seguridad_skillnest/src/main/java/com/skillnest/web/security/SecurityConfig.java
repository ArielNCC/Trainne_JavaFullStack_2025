package com.skillnest.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
   @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	// Rutas públicas
            	.requestMatchers("/", "/login", "/registro", "/registro/guardar").permitAll()
            	// Rutas protegidas por rol ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Rutas protegidas para cualquier usuario autenticado
                .requestMatchers("/perfil/**").authenticated()
                .requestMatchers("/panel").authenticated()
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
            		.loginPage("/login")
            		.defaultSuccessUrl("/panel", true)
            		.failureUrl("/login?error=true")
            		.permitAll())
            .logout(logout -> logout
            		.logoutUrl("/logout")
            		.logoutSuccessUrl("/login?logout=true")
            		.invalidateHttpSession(true)
            		.deleteCookies("JSESSIONID")
            		.permitAll()
            		);
        return http.build();
    }
}