-- Crear el usuario 'Modulo5'
CREATE USER 'Modulo5'@'%' IDENTIFIED BY 'modulo5';

-- Otorgar todos los privilegios al usuario 'Modulo5' en la base de datos 'gestion_productos'
GRANT ALL PRIVILEGES ON gestion_productos.* TO 'Modulo5'@'%';

-- Aplicar los cambios
FLUSH PRIVILEGES;