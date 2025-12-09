package com.skillnest.producto_mvc.service;

import com.skillnest.producto_mvc.model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario autenticar(String username, String password);
    Usuario buscarPorUsername(String username);
    Usuario buscarPorId(Integer id);
    List<Usuario> listarTodos();
    void guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(Integer id);
}
