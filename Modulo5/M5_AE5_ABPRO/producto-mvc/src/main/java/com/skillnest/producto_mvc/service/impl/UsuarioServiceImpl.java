package com.skillnest.producto_mvc.service.impl;

import com.skillnest.producto_mvc.dao.UsuarioDAO;
import com.skillnest.producto_mvc.model.Usuario;
import com.skillnest.producto_mvc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Override
    public Usuario autenticar(String username, String password) {
        if (usuarioDAO.authenticate(username, password)) {
            return usuarioDAO.findByUsername(username);
        }
        return null;
    }
    
    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioDAO.findByUsername(username);
    }
    
    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioDAO.findById(id);
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.findAll();
    }
    
    @Override
    public void guardar(Usuario usuario) {
        usuarioDAO.save(usuario);
    }
    
    @Override
    public void actualizar(Usuario usuario) {
        usuarioDAO.update(usuario);
    }
    
    @Override
    public void eliminar(Integer id) {
        usuarioDAO.delete(id);
    }
}
