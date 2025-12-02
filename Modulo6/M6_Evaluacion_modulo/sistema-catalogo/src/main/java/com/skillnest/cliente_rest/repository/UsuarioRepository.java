package com.skillnest.cliente_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.skillnest.cliente_rest.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);
}
