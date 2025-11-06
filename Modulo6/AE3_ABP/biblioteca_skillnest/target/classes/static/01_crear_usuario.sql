-- Crear usuario accesible desde cualquier host (recomendado en Docker)
CREATE USER IF NOT EXISTS 'Biblioteca_admin'@'%' IDENTIFIED BY 'admin1234';

-- Otorgar privilegios sobre la base de datos biblioteca
GRANT ALL PRIVILEGES ON biblioteca.* TO 'Biblioteca_admin'@'%';

-- Aplicar cambios
FLUSH PRIVILEGES;

-- Verificar usuario
SELECT User, Host FROM mysql.user WHERE User = 'Biblioteca_admin';
