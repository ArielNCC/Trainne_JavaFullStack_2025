package com.skillnest.formulario_demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static ConexionDB instancia;
    private Connection conexion;
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3307/gestion_productos";
    private static final String USUARIO = "Modulo5";
    private static final String PASSWORD = "modulo5";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Constructor privado para implementar Singleton
    private ConexionDB() {
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            // Establecer la conexión
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión establecida exitosamente con la base de datos");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static ConexionDB getInstancia() {
        if (instancia == null || !isConexionValida()) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    // Método para verificar si la conexión es válida
    private static boolean isConexionValida() {
        try {
            return instancia != null && 
                   instancia.conexion != null && 
                   !instancia.conexion.isClosed() && 
                   instancia.conexion.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    // Método público para obtener la conexión
    public Connection getConexion() {
        try {
            // Si la conexión está cerrada o no es válida, reconectar
            if (conexion == null || conexion.isClosed() || !conexion.isValid(2)) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar o restablecer la conexión");
            e.printStackTrace();
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada exitosamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }
}
