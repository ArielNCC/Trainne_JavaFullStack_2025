-- ===============================
-- Script para crear usuario fdc-admin
-- ===============================
-- Ejecutar como usuario root o con privilegios de administrador
CREATE USER IF NOT EXISTS 'fdc-admin'@'%' IDENTIFIED BY 'fdc1234';
GRANT ALL PRIVILEGES ON chile_fdc.* TO 'fdc-admin'@'%';
FLUSH PRIVILEGES;