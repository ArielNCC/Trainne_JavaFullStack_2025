-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS rest_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE rest_db;

-------------------------------------------------------
-- TABLA USUARIOS (para autenticación JWT)
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,      -- hash bcrypt (~60 chars)
    rol VARCHAR(50) NOT NULL DEFAULT 'USER',  -- ADMIN / USER
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-------------------------------------------------------
-- TABLA PRODUCTOS
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    descripcion TEXT
) ENGINE=InnoDB;

-- Contraseñas: admin123 y usuario123
-- Estos hashes fueron generados con BCrypt strength 10
INSERT INTO usuarios (nombre_usuario, email, contrasena, rol)
VALUES 
('admin', 'admin@correo.com', '$2a$10$x8QuP4XX/nXm4.S40pz1fewjHjhCVVI0k9LHqmWQKd02CutLbo3E.', 'ADMIN'),
('usuario1', 'usuario1@correo.com', '$2a$10$4GojV3MgLI6qKQSfLRfRXO8yoOhf1YD7aOzQ1n16p0K6PVrZ8gO8W', 'USER')
ON DUPLICATE KEY UPDATE contrasena = VALUES(contrasena);

INSERT INTO productos (nombre, precio, descripcion) VALUES
('Teclado mecánico', 49990, 'Teclado mecánico retroiluminado RGB'),
('Mouse gamer', 29990, 'Mouse de alta precisión 16000 DPI'),
('Pantalla LED 24"', 129990, 'Monitor LED resolución 1080p');