/* ======================================================
   PROYECTO PERSONAL - BASE DE DATOS "chile_fdc"
   PORTAFOLIO - GESTIÓN DE BASES DE DATOS RELACIONALES
   ====================================================== */

/* ======================================================
   1. CREACIÓN DE BASE DE DATOS (DDL)
   ====================================================== */

DROP DATABASE IF EXISTS `chile_fdc`;
CREATE DATABASE `chile_fdc` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `chile_fdc`;

/* ------------------------------------------------------
   Características de una base de datos relacional:
   - La información se almacena en TABLAS
   - Cada tabla tiene REGISTROS (filas)
   - Cada registro tiene CAMPOS (columnas)
   - Se establecen relaciones mediante claves foráneas
--------------------------------------------------------- */

/* ======================================================
   2. CREACIÓN DE TABLAS (DDL)
   ====================================================== */

-- ============================
-- TABLA PRINCIPAL: ALIMENTO
-- ============================
/*
   Representa los alimentos obtenidos desde la API (FDC).
   Clave primaria: id_alimento
*/
CREATE TABLE alimento (
    id_alimento      BIGINT PRIMARY KEY,         -- fdcId (PK)
    descripcion      VARCHAR(255) NOT NULL,      -- nombre del alimento
    brand_owner      VARCHAR(255)                -- opcional
) ENGINE=InnoDB;

-- ============================
-- TABLA: NUTRIENTE
-- ============================
/*
   Catálogo de nutrientes. 
   Relación con alimento mediante tabla puente.
*/
CREATE TABLE nutriente (
    id_nutriente     BIGINT PRIMARY KEY,         -- nutrientId (PK)
    nombre           VARCHAR(255) NOT NULL,
    unidad_medida    VARCHAR(20),                -- g, mg, IU, etc.
    descripcion      VARCHAR(255)                -- opcional
) ENGINE=InnoDB;

-- ===========================================
-- TABLA PUENTE: ALIMENTO_NUTRIENTE (N:M)
-- ===========================================
CREATE TABLE alimento_nutriente (
    id_alimento      BIGINT NOT NULL,
    id_nutriente     BIGINT NOT NULL,
    valor            DOUBLE,                     -- valor nutricional
    PRIMARY KEY (id_alimento, id_nutriente),

    -- Relaciones
    CONSTRAINT fk_alimento FOREIGN KEY (id_alimento)
        REFERENCES alimento(id_alimento)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_nutriente FOREIGN KEY (id_nutriente)
        REFERENCES nutriente(id_nutriente)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ===========================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ===========================================
CREATE INDEX idx_alimento_descripcion ON alimento(descripcion);
CREATE INDEX idx_nutriente_nombre ON nutriente(nombre);
CREATE INDEX idx_alimento_nutriente_valor ON alimento_nutriente(valor);

/* ======================================================
   3. INSERCIÓN DE DATOS (DML)
   ====================================================== */

-- Insertar un alimento de ejemplo (desde la API)
INSERT INTO alimento (id_alimento, descripcion, brand_owner)
VALUES (1750340, 'Apples, fuji, with skin, raw', NULL);

-- Insertar nutrientes
INSERT INTO nutriente (id_nutriente, nombre, unidad_medida)
VALUES 
    (1051, 'Water', 'g'),
    (2045, 'Proximates', NULL);

-- Relación alimento-nutrientes
INSERT INTO alimento_nutriente (id_alimento, id_nutriente, valor)
VALUES (1750340, 1051, 83.61);

/* ======================================================
   4. ACTUALIZACIÓN Y ELIMINACIÓN (DML)
   ====================================================== */

-- Actualizar texto de alimento
UPDATE alimento
SET descripcion = 'Apple, Fuji, raw (updated)'
WHERE id_alimento = 1750340;

-- Eliminar relación (si fuera necesario)
DELETE FROM alimento_nutriente
WHERE id_alimento = 1750340 AND id_nutriente = 2045;

/* ======================================================
   5. CONSULTAS SQL (SELECT, JOIN, WHERE, GROUP BY)
   ====================================================== */

-- 1. Consultar todos los nutrientes de un alimento
SELECT a.descripcion, n.nombre, an.valor, n.unidad_medida
FROM alimento a
JOIN alimento_nutriente an ON an.id_alimento = a.id_alimento
JOIN nutriente n ON n.id_nutriente = an.id_nutriente
WHERE a.id_alimento = 1750340;

-- 2. Buscar alimentos por palabra clave
SELECT * FROM alimento
WHERE descripcion LIKE '%apple%';

-- 3. Cantidad de nutrientes por alimento (GROUP BY)
SELECT a.descripcion, COUNT(*) AS cantidad_nutrientes
FROM alimento a
JOIN alimento_nutriente an ON an.id_alimento = a.id_alimento
GROUP BY a.descripcion;

/* ======================================================
   6. MODELO ENTIDAD-RELACIÓN (ER DIAGRAM ASCII)
   ======================================================

        +-------------+               +--------------+
        |  ALIMENTO   |               |  NUTRIENTE   |
        +-------------+               +--------------+
        | id_alimento |<---\     /--->| id_nutriente |
        | descripcion |     \   /     | nombre       |
        | brand_owner |      \ /      | unidad_medida|
        +-------------+       X       +--------------+
                                      
                                N:M
                             (ALIMENTO_NUTRIENTE)
                         +----------------------------+
                         | id_alimento (FK)           |
                         | id_nutriente (FK)          |
                         | valor                      |
                         +----------------------------+

   Explicación:
   - ALIMENTO y NUTRIENTE no dependen entre sí → 1FN, 2FN, 3FN cumplidas.
   - La tabla puente evita duplicación y maneja la relación N:M adecuadamente.
====================================================== */

/* ======================================================
   7. CREACIÓN DE VISTAS (VISUALIZACIONES LÓGICAS)
   ====================================================== */

CREATE OR REPLACE VIEW vista_contenido_nutricional_por_id AS
SELECT 
    a.id_alimento,
    a.descripcion AS alimento,
    a.brand_owner,
    n.id_nutriente,
    n.nombre AS nutriente,
    an.valor AS cantidad,
    n.unidad_medida
FROM alimento a
JOIN alimento_nutriente an ON a.id_alimento = an.id_alimento
JOIN nutriente n ON n.id_nutriente = an.id_nutriente;

-- Vista 2: Consulta resumida de nutrientes clave por palabra en la descripción
CREATE OR REPLACE VIEW vista_nutrientes_clave_por_descripcion AS
SELECT 
    a.id_alimento,
    a.descripcion,
    MAX(CASE WHEN n.id_nutriente = 1051 THEN an.valor END) AS agua,         -- Agua
    MAX(CASE WHEN n.id_nutriente = 1003 THEN an.valor END) AS proteinas,    -- Proteína
    MAX(CASE WHEN n.id_nutriente = 1008 THEN an.valor END) AS calorias,     -- Calorías
    MAX(CASE WHEN n.id_nutriente = 1004 THEN an.valor END) AS grasa_total   -- Grasa total
FROM alimento a
LEFT JOIN alimento_nutriente an ON a.id_alimento = an.id_alimento
LEFT JOIN nutriente n ON n.id_nutriente = an.id_nutriente
GROUP BY a.id_alimento, a.descripcion;