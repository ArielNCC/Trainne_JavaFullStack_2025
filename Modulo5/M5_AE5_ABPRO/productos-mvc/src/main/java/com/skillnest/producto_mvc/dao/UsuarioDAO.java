package com.skillnest.producto_mvc.dao;

import com.skillnest.producto_mvc.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    Usuario findByUsername(String username);
    Usuario findById(Integer id);
    List<Usuario> findAll();
    void save(Usuario usuario);
    void update(Usuario usuario);
    void delete(Integer id);
    boolean authenticate(String username, String password);
}
