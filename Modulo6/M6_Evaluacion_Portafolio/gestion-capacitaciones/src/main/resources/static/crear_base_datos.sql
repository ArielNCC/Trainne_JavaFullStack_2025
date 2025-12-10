-- Limpiar si ya existen (solo para desarrollo)
DROP DATABASE IF EXISTS gestion_capacitaciones;
CREATE DATABASE gestion_capacitaciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_capacitaciones;

-- ========================================
-- TABLAS DE SEGURIDAD
-- ========================================

-- Tabla de roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_role_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla intermedia usuarios-roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLAS DE NEGOCIO
-- ========================================

-- Tabla de instructores
CREATE TABLE instructores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    especialidad VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_instructor_email (email),
    INDEX idx_instructor_nombre (nombre, apellido)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de cursos
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    duracion_horas INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    cupo_maximo INT NOT NULL DEFAULT 30,
    instructor_id BIGINT,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES instructores(id) ON DELETE SET NULL,
    INDEX idx_curso_nombre (nombre),
    INDEX idx_curso_fechas (fecha_inicio, fecha_fin),
    INDEX idx_curso_instructor (instructor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de empleados
CREATE TABLE empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    departamento VARCHAR(100),
    cargo VARCHAR(100),
    user_id BIGINT UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_empleado_email (email),
    INDEX idx_empleado_nombre (nombre, apellido),
    INDEX idx_empleado_departamento (departamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de inscripciones
CREATE TABLE inscripciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    empleado_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) DEFAULT 'INSCRITO',
    nota_final DECIMAL(5,2),
    asistencia_porcentaje DECIMAL(5,2),
    aprobado BOOLEAN DEFAULT FALSE,
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE,
    UNIQUE KEY unique_inscripcion (empleado_id, curso_id),
    INDEX idx_inscripcion_estado (estado),
    INDEX idx_inscripcion_fecha (fecha_inscripcion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- DATOS INICIALES
-- ========================================

-- Insertar roles
INSERT INTO roles (name, description) VALUES 
('ADMIN', 'Administrador del sistema con acceso completo'),
('EMPLEADO', 'Empleado que puede ver e inscribirse en cursos');

-- Insertar usuarios (password: admin123 y empleado123 - BCrypt)
-- Hash generado con BCryptPasswordEncoder
INSERT INTO users (username, password, email, enabled) VALUES 
('admin', '$2b$12$PW0axPMWxuzi38qKcHVMdeXj4vJOeBXjX/xJcp3/UkaUypte7Ncxm', 'admin@empresa.com', TRUE),
('empleado1', '$2b$12$G/UZfIQr3cL9jL38U5T36.mmQNHG5JlviGU2JIXMmlDqX3Lllcbzq', 'empleado1@empresa.com', TRUE),
('empleado2', '$2b$12$G/UZfIQr3cL9jL38U5T36.mmQNHG5JlviGU2JIXMmlDqX3Lllcbzq', 'empleado2@empresa.com', TRUE);

-- Asignar roles a usuarios
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- admin tiene rol ADMIN
(2, 2), -- empleado1 tiene rol EMPLEADO
(3, 2); -- empleado2 tiene rol EMPLEADO

-- Insertar instructores
INSERT INTO instructores (nombre, apellido, email, telefono, especialidad) VALUES 
('Juan', 'Pérez', 'juan.perez@empresa.com', '+56912345678', 'Desarrollo de Software'),
('María', 'González', 'maria.gonzalez@empresa.com', '+56923456789', 'Gestión de Proyectos'),
('Carlos', 'Rodríguez', 'carlos.rodriguez@empresa.com', '+56934567890', 'Seguridad Informática'),
('Ana', 'Martínez', 'ana.martinez@empresa.com', '+56945678901', 'Base de Datos'),
('Luis', 'Fernández', 'luis.fernandez@empresa.com', '+56956789012', 'Cloud Computing');

-- Insertar empleados
INSERT INTO empleados (nombre, apellido, email, telefono, departamento, cargo, user_id) VALUES 
('Pedro', 'Sánchez', 'empleado1@empresa.com', '+56967890123', 'Desarrollo', 'Desarrollador Junior', 2),
('Laura', 'Torres', 'empleado2@empresa.com', '+56978901234', 'Desarrollo', 'Desarrollador Senior', 3),
('Miguel', 'Ramírez', 'miguel.ramirez@empresa.com', '+56989012345', 'QA', 'Tester', NULL),
('Sofia', 'López', 'sofia.lopez@empresa.com', '+56990123456', 'DevOps', 'DevOps Engineer', NULL),
('Diego', 'Castro', 'diego.castro@empresa.com', '+56901234567', 'Desarrollo', 'Full Stack Developer', NULL);

-- Insertar cursos (Fechas actualizadas para que aparezcan disponibles)
INSERT INTO cursos (nombre, descripcion, duracion_horas, fecha_inicio, fecha_fin, cupo_maximo, instructor_id, activo) VALUES 
('Introducción a Spring Boot', 'Curso básico de Spring Boot para principiantes', 40, DATE_ADD(CURRENT_DATE, INTERVAL 1 MONTH), DATE_ADD(CURRENT_DATE, INTERVAL 2 MONTH), 25, 1, TRUE),
('Java Avanzado', 'Programación avanzada en Java: Streams, Lambda, Collections', 60, DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 45 DAY), 20, 1, TRUE),
('Metodologías Ágiles', 'Scrum, Kanban y gestión ágil de proyectos', 30, DATE_ADD(CURRENT_DATE, INTERVAL 2 MONTH), DATE_ADD(CURRENT_DATE, INTERVAL 3 MONTH), 30, 2, TRUE),
('Seguridad en Aplicaciones Web', 'OWASP, autenticación, autorización y mejores prácticas', 50, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 40 DAY), 20, 3, TRUE),
('Bases de Datos SQL', 'MySQL, consultas avanzadas, optimización y diseño', 45, DATE_ADD(CURRENT_DATE, INTERVAL 3 MONTH), DATE_ADD(CURRENT_DATE, INTERVAL 4 MONTH), 25, 4, TRUE),
('AWS Fundamentos', 'Introducción a servicios de Amazon Web Services', 35, DATE_ADD(CURRENT_DATE, INTERVAL 20 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 50 DAY), 20, 5, TRUE),
('Docker y Kubernetes', 'Contenedores y orquestación en la nube', 55, DATE_ADD(CURRENT_DATE, INTERVAL 4 MONTH), DATE_ADD(CURRENT_DATE, INTERVAL 5 MONTH), 15, 5, TRUE),
('React para Principiantes', 'Desarrollo frontend con React y hooks', 40, DATE_ADD(CURRENT_DATE, INTERVAL 5 MONTH), DATE_ADD(CURRENT_DATE, INTERVAL 6 MONTH), 25, 1, FALSE);

-- Insertar algunas inscripciones de ejemplo
INSERT INTO inscripciones (empleado_id, curso_id, estado, nota_final, asistencia_porcentaje, aprobado) VALUES 
(1, 1, 'INSCRITO', NULL, NULL, FALSE),
(1, 3, 'INSCRITO', NULL, NULL, FALSE),
(2, 1, 'COMPLETADO', 6.5, 95.00, TRUE),
(2, 2, 'EN_CURSO', NULL, 80.00, FALSE),
(3, 4, 'INSCRITO', NULL, NULL, FALSE),
(4, 5, 'INSCRITO', NULL, NULL, FALSE),
(5, 2, 'COMPLETADO', 5.8, 90.00, TRUE);

