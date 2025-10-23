-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS agendacontactos;

-- Usar la base de datos
USE agendacontactos;

-- Eliminar tabla si existe (para reinicios limpios)
DROP TABLE IF EXISTS contactos;

-- Crear la tabla contactos con sintaxis MySQL
CREATE TABLE contactos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insertar datos de prueba
INSERT INTO contactos (nombre, correo, telefono) VALUES 
('Juan Pérez', 'juan@example.com', '+56912345678'),
('María García', 'maria@example.com', '+56987654321'),
('Carlos López', 'carlos@example.com', '+56955555555'),
('Ana Silva', 'ana.silva@empresa.com', '+56944444444'),
('Pedro Rodríguez', 'pedro.rodriguez@correo.cl', '+56933333333'),
('Luis Martínez', 'luis.martinez@gmail.com', '+56922222222'),
('Carmen Díaz', 'carmen.diaz@hotmail.com', '+56911111111'),
('Roberto Flores', 'roberto.flores@yahoo.com', '+56900000000'),
('Elena Vargas', 'elena.vargas@outlook.com', '+56988888888'),
('Diego Morales', 'diego.morales@empresa.cl', '+56977777777');

-- Verificar que los datos se insertaron correctamente
SELECT 'Datos insertados correctamente' AS resultado;
SELECT COUNT(*) AS total_contactos FROM contactos;
SELECT * FROM contactos ORDER BY fecha_creacion;