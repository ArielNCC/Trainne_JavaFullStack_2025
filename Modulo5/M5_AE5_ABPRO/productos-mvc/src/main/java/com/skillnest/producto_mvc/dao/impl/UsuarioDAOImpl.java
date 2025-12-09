package com.skillnest.producto_mvc.dao.impl;

import com.skillnest.producto_mvc.dao.UsuarioDAO;
import com.skillnest.producto_mvc.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UsuarioDAOImpl implements UsuarioDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Usuario findByUsername(String username) {
        try {
            return entityManager.createQuery(
                    "SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Usuario findById(Integer id) {
        return entityManager.find(Usuario.class, id);
    }
    
    @Override
    public List<Usuario> findAll() {
        return entityManager.createQuery("SELECT u FROM Usuario u ORDER BY u.id", Usuario.class)
                .getResultList();
    }
    
    @Override
    public void save(Usuario usuario) {
        entityManager.persist(usuario);
    }
    
    @Override
    public void update(Usuario usuario) {
        entityManager.merge(usuario);
    }
    
    @Override
    public void delete(Integer id) {
        Usuario usuario = findById(id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        Usuario usuario = findByUsername(username);
        return usuario != null && usuario.getPassword().equals(password) && usuario.getActivo();
    }
}
