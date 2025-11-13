package com.skillnest.producto_mvc.dao.impl;

import com.skillnest.producto_mvc.dao.ProductoDAO;
import com.skillnest.producto_mvc.model.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class ProductoDAOImpl implements ProductoDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Producto findById(Integer id) {
        return entityManager.find(Producto.class, id);
    }
    
    @Override
    public List<Producto> findAll() {
        return entityManager.createQuery(
                "SELECT p FROM Producto p ORDER BY p.id", Producto.class)
                .getResultList();
    }
    
    @Override
    public List<Producto> findByCategoria(String categoria) {
        return entityManager.createQuery(
                "SELECT p FROM Producto p WHERE p.categoria = :categoria ORDER BY p.nombre", Producto.class)
                .setParameter("categoria", categoria)
                .getResultList();
    }
    
    @Override
    public List<Producto> findActivos() {
        return entityManager.createQuery(
                "SELECT p FROM Producto p WHERE p.activo = true ORDER BY p.nombre", Producto.class)
                .getResultList();
    }
    
    @Override
    public void save(Producto producto) {
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setFechaModificacion(LocalDateTime.now());
        entityManager.persist(producto);
    }
    
    @Override
    public void update(Producto producto) {
        producto.setFechaModificacion(LocalDateTime.now());
        entityManager.merge(producto);
    }
    
    @Override
    public void delete(Integer id) {
        Producto producto = findById(id);
        if (producto != null) {
            entityManager.remove(producto);
        }
    }
}
