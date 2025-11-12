-- Eliminar la base de datos si existe (opcional, para desarrollo)
DROP DATABASE IF EXISTS gestion_productos;

-- Crear la base de datos
CREATE DATABASE gestion_productos;

-- Seleccionar la base de datos
USE gestion_productos;

-- Crear la tabla productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion TEXT
);

-- Insertar datos de prueba
INSERT INTO productos (nombre, precio, descripcion) VALUES
('Laptop Dell Inspiron', 899.99, 'Laptop de alto rendimiento con procesador Intel Core i7, 16GB RAM y 512GB SSD'),
('Mouse Logitech MX Master', 99.99, 'Mouse ergonómico inalámbrico con sensor de alta precisión'),
('Teclado Mecánico Corsair', 149.99, 'Teclado mecánico RGB con switches Cherry MX Red'),
('Monitor Samsung 27"', 299.99, 'Monitor LED Full HD con tecnología AMD FreeSync'),
('Webcam Logitech C920', 79.99, 'Cámara web Full HD 1080p con micrófono incorporado'),
('Auriculares Sony WH-1000XM4', 349.99, 'Auriculares inalámbricos con cancelación de ruido activa'),
('Disco Duro Externo 2TB', 89.99, 'Almacenamiento portátil USB 3.0 de alta velocidad'),
('Tarjeta Gráfica NVIDIA RTX 3060', 499.99, 'GPU para gaming y edición de video profesional');

-- Consulta para verificar los datos
SELECT * FROM productos;