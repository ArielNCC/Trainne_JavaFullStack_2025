-- Script para crear la base de datos para la Biblioteca Universitaria
-- Sistema de Gestión de Libros y Solicitudes de Préstamo

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS biblioteca_universitaria;
USE biblioteca_universitaria;

-- Tabla de libros
CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    disponible BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_titulo (titulo),
    INDEX idx_autor (autor),
    INDEX idx_disponible (disponible)
);

-- Tabla de solicitudes de préstamo
CREATE TABLE IF NOT EXISTS solicitudes_prestamo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(100) NOT NULL,
    correo_usuario VARCHAR(100) NOT NULL,
    libro_id INT NOT NULL,
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    INDEX idx_libro (libro_id),
    INDEX idx_estado (estado),
    FOREIGN KEY (libro_id) REFERENCES libros(id) ON DELETE CASCADE
);

-- Insertar libros de ejemplo
INSERT INTO libros (titulo, autor, isbn, disponible) VALUES
('Cien Años de Soledad', 'Gabriel García Márquez', '978-0307474728', TRUE),
('Don Quijote de la Mancha', 'Miguel de Cervantes', '978-8491050384', TRUE),
('1984', 'George Orwell', '978-0451524935', TRUE),
('El Principito', 'Antoine de Saint-Exupéry', '978-0156012195', TRUE),
('Rayuela', 'Julio Cortázar', '978-8420471341', TRUE),
('La Sombra del Viento', 'Carlos Ruiz Zafón', '978-8408093107', FALSE),
('Crónica de una Muerte Anunciada', 'Gabriel García Márquez', '978-0307387325', TRUE),
('El Amor en los Tiempos del Cólera', 'Gabriel García Márquez', '978-0307387738', TRUE),
('Los Detectives Salvajes', 'Roberto Bolaño', '978-0312427481', FALSE),
('Ficciones', 'Jorge Luis Borges', '978-0802130303', TRUE),
('Pedro Páramo', 'Juan Rulfo', '978-8437605944', TRUE),
('La Casa de los Espíritus', 'Isabel Allende', '978-0553383805', TRUE);

-- Insertar solicitudes de préstamo de ejemplo
INSERT INTO solicitudes_prestamo (nombre_usuario, correo_usuario, libro_id, estado) VALUES
('Juan Pérez García', 'juan.perez@universidad.edu', 1, 'APROBADA'),
('María González López', 'maria.gonzalez@universidad.edu', 3, 'PENDIENTE'),
('Carlos Rodríguez', 'carlos.rodriguez@universidad.edu', 5, 'PENDIENTE'),
('Ana Silva Torres', 'ana.silva@universidad.edu', 2, 'APROBADA');

-- Consultas de verificación
SELECT 'Base de datos creada exitosamente' AS Status;
SELECT CONCAT('Total de libros registrados: ', COUNT(*)) AS Info FROM libros;
SELECT CONCAT('Total de solicitudes registradas: ', COUNT(*)) AS Info FROM solicitudes_prestamo;
SELECT * FROM libros ORDER BY id;
SELECT * FROM solicitudes_prestamo ORDER BY id;
