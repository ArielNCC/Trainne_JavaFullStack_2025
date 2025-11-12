package com.skillnest.formulario_demo.dao;

import com.skillnest.formulario_demo.config.ConexionDB;
import com.skillnest.formulario_demo.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImp implements ProductoDAO {
    
    private Connection conexion;

    public ProductoDAOImp() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    @Override
    public boolean crear(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, descripcion) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setString(3, producto.getDescripcion());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al crear producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY id";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("descripcion")
                );
                productos.add(producto);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return productos;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        Producto producto = null;
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return producto;
    }
}
