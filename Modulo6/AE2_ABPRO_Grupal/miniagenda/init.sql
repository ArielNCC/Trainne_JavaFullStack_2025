-- Script de inicialización de la base de datos Mini Agenda
-- Se ejecuta automáticamente al levantar el contenedor de MySQL

-- Seleccionar la base de datos
USE miniagenda;

-- ============================================
-- CREACIÓN DE LA TABLA DE EVENTOS
-- ============================================

-- Eliminar la tabla si existe (para reinicios limpios)
DROP TABLE IF EXISTS eventos;

-- Crear la tabla eventos con todas las columnas necesarias
CREATE TABLE eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único del evento',
    titulo VARCHAR(100) NOT NULL COMMENT 'Título del evento',
    fecha DATE NOT NULL COMMENT 'Fecha en que se realizará el evento',
    descripcion VARCHAR(500) NOT NULL COMMENT 'Descripción detallada del evento',
    responsable VARCHAR(100) NOT NULL COMMENT 'Persona responsable del evento',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro',
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Fecha de última modificación'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de eventos de la agenda compartida';

-- ============================================
-- CREACIÓN DE ÍNDICES PARA OPTIMIZACIÓN
-- ============================================

-- Índice para búsquedas por fecha (muy común en la aplicación)
CREATE INDEX idx_fecha ON eventos(fecha);

-- Índice para búsquedas por responsable
CREATE INDEX idx_responsable ON eventos(responsable);

-- Índice compuesto para búsquedas por fecha y responsable
CREATE INDEX idx_fecha_responsable ON eventos(fecha, responsable);

-- ============================================
-- INSERCIÓN DE DATOS DE PRUEBA
-- ============================================

-- Eventos de ejemplo para octubre 2025
INSERT INTO eventos (titulo, fecha, descripcion, responsable) VALUES
('Reunión de Equipo Desarrollo', '2025-10-25', 'Reunión semanal del equipo de desarrollo para revisar avances del sprint actual y planificar tareas', 'Juan Pérez'),
('Presentación Proyecto Cliente', '2025-10-26', 'Presentación del avance del proyecto al cliente principal, incluye demo en vivo', 'María García'),
('Capacitación Spring Boot', '2025-10-28', 'Taller de capacitación sobre buenas prácticas en Spring Boot para el equipo técnico', 'Carlos Rodríguez');

-- Eventos de ejemplo para noviembre 2025
INSERT INTO eventos (titulo, fecha, descripcion, responsable) VALUES
('Cumpleaños de María', '2025-11-05', 'Celebración del cumpleaños de María en la oficina, habrá pastel a las 4pm', 'Ana López'),
('Revisión de Código Mensual', '2025-11-10', 'Sesión mensual de revisión de código y refactorización, todos los desarrolladores participan', 'Pedro Martínez'),
('Planning Sprint 12', '2025-11-15', 'Planificación del sprint 12, estimación de historias de usuario y asignación de tareas', 'Juan Pérez'),

-- Eventos de ejemplo para diciembre 2025
INSERT INTO eventos (titulo, fecha, descripcion, responsable) VALUES
('Convivencia Anual', '2025-12-15', 'Convivencia de fin de año con todos los empleados de la empresa, incluye cena y regalos', 'Recursos Humanos'),
('Cierre de Proyectos 2025', '2025-12-20', 'Revisión y cierre formal de todos los proyectos del año 2025, documentación y lecciones aprendidas', 'Gerencia de Proyectos'),
('Última Reunión del Año', '2025-12-23', 'Última reunión del año para revisar logros y establecer objetivos para el próximo año', 'Dirección General');

-- Eventos futuros para 2026
INSERT INTO eventos (titulo, fecha, descripcion, responsable) VALUES
('Kickoff 2026', '2026-01-07', 'Reunión de inicio del año para presentar objetivos estratégicos y nuevos proyectos', 'Dirección General'),
('Workshop Microservicios', '2026-01-15', 'Taller práctico sobre arquitectura de microservicios y mejores prácticas de implementación', 'Carlos Rodríguez'),
('Auditoría de Seguridad', '2026-02-01', 'Auditoría trimestral de seguridad informática y revisión de políticas de acceso', 'Departamento IT');
