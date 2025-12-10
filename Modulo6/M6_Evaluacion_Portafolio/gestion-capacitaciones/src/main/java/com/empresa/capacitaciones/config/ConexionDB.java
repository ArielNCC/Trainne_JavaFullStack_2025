package com.empresa.capacitaciones.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración para gestionar la conexión a la base de datos.
 * Lee las credenciales desde application.properties y proporciona el DataSource
 * que será usado por JPA/Hibernate para todas las operaciones de base de datos.
 */
@Configuration
public class ConexionDB {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * Crea y configura el DataSource que será usado por toda la aplicación.
     * Este bean es el punto central de configuración de la base de datos.
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        
        System.out.println("✓ DataSource configurado desde ConexionDB");
        System.out.println("  - URL: " + url);
        System.out.println("  - Usuario: " + username);
        
        return dataSourceBuilder.build();
    }

    /**
     * Obtiene la URL de conexión a la base de datos
     */
    public String getUrl() {
        return url;
    }

    /**
     * Obtiene el nombre de usuario para la conexión
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene la contraseña para la conexión
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el nombre del driver JDBC
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Método para obtener una conexión manual a la base de datos
     * (solo si se necesita fuera de JPA)
     */
    public Connection obtenerConexion() throws SQLException {
        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado: " + driverClassName, e);
        }
    }

    /**
     * Verifica la conexión al iniciar la aplicación
     */
    @PostConstruct
    public void verificarConexion() {
        try (Connection conn = obtenerConexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("✓ CONEXIÓN A BASE DE DATOS ESTABLECIDA");
                System.out.println("  URL:     " + url);
                System.out.println("  Usuario: " + username);
                System.out.println("  Estado:  CONECTADO");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        } catch (SQLException e) {
            System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.err.println("✗ ERROR AL CONECTAR CON LA BASE DE DATOS");
            System.err.println("  URL:     " + url);
            System.err.println("  Usuario: " + username);
            System.err.println("  Error:   " + e.getMessage());
            System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.err.println("\nVerifique que:");
            System.err.println("  1. MySQL esté ejecutándose");
            System.err.println("  2. La base de datos 'gestion_capacitaciones' exista");
            System.err.println("  3. Las credenciales en application.properties sean correctas");
        }
    }

    /**
     * Cierra una conexión de forma segura
     */
    public void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra información de la configuración de la base de datos
     */
    public void mostrarConfiguracion() {
        System.out.println("=== Configuración de Base de Datos ===");
        System.out.println("URL: " + url);
        System.out.println("Usuario: " + username);
        System.out.println("Driver: " + driverClassName);
        System.out.println("=====================================");
    }
}
