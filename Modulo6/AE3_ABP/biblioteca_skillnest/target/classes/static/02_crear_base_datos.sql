-- Limpiar si ya existen (solo para desarrollo)
DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca;

-- Tabla de autores
CREATE TABLE autores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(100),
    fecha_nacimiento DATE,
    biografia TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_apellido (apellido),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de libros
CREATE TABLE libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    anio_publicacion INT,
    genero VARCHAR(50),
    editorial VARCHAR(100),
    numero_paginas INT,
    cantidad_disponible INT DEFAULT 0,
    cantidad_total INT DEFAULT 0,
    autor_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_libro_autor FOREIGN KEY (autor_id)
        REFERENCES autores(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    INDEX idx_titulo (titulo),
    INDEX idx_isbn (isbn),
    INDEX idx_autor_id (autor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos
INSERT INTO autores (nombre, apellido, nacionalidad, fecha_nacimiento, biografia) VALUES
('Gabriel', 'García Márquez', 'Colombiano', '1927-03-06', 'Novelista colombiano, Premio Nobel 1982.'),
('Isabel', 'Allende', 'Chilena', '1942-08-02', 'Escritora chilena de novelas y cuentos.'),
('Jorge', 'Luis Borges', 'Argentino', '1899-08-24', 'Escritor argentino destacado.'),
('Pablo', 'Neruda', 'Chileno', '1904-07-12', 'Poeta chileno, Premio Nobel 1971.'),
('Mario', 'Vargas Llosa', 'Peruano', '1936-03-28', 'Escritor peruano, Premio Nobel 2010.');

INSERT INTO libros (titulo, isbn, anio_publicacion, genero, editorial, numero_paginas, cantidad_disponible, cantidad_total, autor_id) VALUES
('Cien años de soledad', '978-0307474728', 1967, 'Realismo mágico', 'Editorial Sudamericana', 417, 5, 5, 1),
('El amor en los tiempos del cólera', '978-0307387387', 1985, 'Romance', 'Editorial Oveja Negra', 368, 3, 3, 1),
('La casa de los espíritus', '978-8401352836', 1982, 'Ficción histórica', 'Plaza & Janés', 433, 4, 4, 2),
('Paula', '978-8401341007', 1994, 'Memorias', 'Plaza & Janés', 330, 2, 2, 2),
('Ficciones', '978-0802130303', 1944, 'Cuentos', 'Editorial Sur', 174, 6, 6, 3),
('El Aleph', '978-8420633947', 1949, 'Cuentos', 'Losada', 203, 4, 4, 3),
('Veinte poemas de amor y una canción desesperada', '978-8437604695', 1924, 'Poesía', 'Nascimento', 132, 7, 7, 4),
('La ciudad y los perros', '978-8432217203', 1963, 'Ficción', 'Seix Barral', 403, 3, 3, 5),
('La fiesta del chivo', '978-8420471464', 2000, 'Ficción histórica', 'Alfaguara', 517, 2, 2, 5);
