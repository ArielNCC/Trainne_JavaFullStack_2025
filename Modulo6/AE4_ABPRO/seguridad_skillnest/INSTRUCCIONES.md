# SkillNest - Sistema de Autenticación y Autorización

Sistema básico de autenticación y autorización que usa Spring Security y JPA para gestionar usuarios con control de acceso basado en roles.

## 🎯 Objetivos del Proyecto

- Implementar autenticación de usuarios con Spring Security
- Control de acceso basado en roles (ADMIN y USER)
- Persistencia de usuarios con JPA/MySQL
- Encriptación de contraseñas con BCrypt
- Interfaz web con Thymeleaf

## 📋 Requisitos Previos

- Java 21
- Maven
- Docker (para MySQL)
- Spring Tool Suite (STS) o cualquier IDE compatible

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.5.7**
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **MySQL** - Base de datos
- **Thymeleaf** - Motor de plantillas
- **Bootstrap 5** - Estilos CSS
- **BCrypt** - Encriptación de contraseñas
- **Lombok** - Reducir código boilerplate

## 🚀 Configuración de la Base de Datos

### 1. Crear contenedor Docker con MySQL

```bash
docker run --name mysql-skillnest -e MYSQL_ROOT_PASSWORD=rootpassword -p 3307:3306 -d mysql:8.0
```

### 2. Acceder al contenedor

```bash
docker exec -it mysql-skillnest mysql -u root -p
# Password: rootpassword
```

### 3. Ejecutar scripts SQL

Dentro del cliente MySQL, ejecuta los siguientes comandos:

```sql
-- Crear usuario
CREATE USER IF NOT EXISTS 'seguridad'@'%' IDENTIFIED BY 'admin1234';

-- Crear base de datos
DROP DATABASE IF EXISTS seguridad;
CREATE DATABASE seguridad CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE seguridad;

-- Otorgar privilegios
GRANT ALL PRIVILEGES ON seguridad.* TO 'seguridad'@'%';
FLUSH PRIVILEGES;

-- Crear tabla usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Nota:** Los scripts también están disponibles en:
- `src/main/resources/static/crear_usuario.sql`
- `src/main/resources/static/crear_base_Datos.sql`

## ▶️ Ejecución del Proyecto

### 1. Compilar el proyecto

```bash
mvn clean install
```

### 2. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O desde tu IDE, ejecutar la clase `SeguridadApplication.java`

### 3. Acceder a la aplicación

Abre tu navegador en: **http://localhost:8081**

## 👥 Rutas de Acceso

### Rutas Públicas (sin autenticación)
- `/` - Redirección a `/login`
- `/login` - Página de inicio (landing) y formulario de autenticación
- `/registro` - Formulario de registro

### Rutas Autenticadas (requiere login)
- `/panel` - Panel general (usuarios autenticados)
- `/perfil/detalle` - Perfil del usuario (usuarios autenticados)

### Rutas Protegidas por Rol
- `/admin/**` - Solo accesible para usuarios con rol **ADMIN**

## 🔐 Sistema de Roles

El sistema implementa dos roles:

1. **USER** - Usuario regular
   - Acceso a `/panel`, `/perfil/detalle`
   
2. **ADMIN** - Administrador
   - Acceso a todas las rutas de USER
   - Acceso adicional a `/admin/detalle` (gestión de usuarios)
   - Acceso a todas las rutas de USER
   - Acceso adicional a `/admin/detalle` (gestión de usuarios)

## 📝 Funcionalidades Implementadas

### Autenticación
- ✅ Formulario de login personalizado
- ✅ Registro de nuevos usuarios
- ✅ Encriptación de contraseñas con BCrypt
- ✅ Validación de credenciales contra base de datos
- ✅ Gestión de sesiones

### Autorización
- ✅ Control de acceso basado en roles
- ✅ Protección de rutas `/admin/**` para ADMIN
- ✅ Protección de rutas `/perfil/**` para autenticados
- ✅ Redirección automática según permisos

### Gestión de Usuarios
- ✅ Lista de usuarios (en `/home` y `/admin/detalle`)
- ✅ Visualización de perfil personal
- ✅ Panel general para usuarios autenticados
- ✅ Panel de administración con estadísticas

### Interfaz Web
- ✅ Diseño responsive con Bootstrap 5
- ✅ Navegación contextual según rol
- ✅ Mensajes de error y éxito
- ✅ Validación de formularios

## 🗂️ Estructura del Proyecto

```
src/main/java/com/skillnest/web/
├── controllers/
│   └── WebController.java          # Controlador de rutas
├── Dto/
│   └── UserDto.java                # DTO para transferencia de datos
├── models/
│   └── Usuario.java                # Entidad JPA Usuario
├── repositories/
│   └── UsuarioRepository.java      # Repositorio JPA
├── security/
│   └── SecurityConfig.java         # Configuración Spring Security
├── services/
│   ├── UserService.java            # Interfaz del servicio
│   └── UsuarioServiceImpl.java     # Implementación del servicio
└── SeguridadApplication.java       # Clase principal

src/main/resources/
├── templates/
│   ├── index.html                  # Página de inicio
│   ├── login.html                  # Formulario de login
│   ├── registro.html               # Formulario de registro
│   ├── home.html                   # Página principal autenticada
│   ├── panel.html                  # Panel general
│   ├── perfil.html                 # Perfil de usuario
│   └── admin.html                  # Panel de administración
├── static/
│   ├── crear_base_Datos.sql        # Script creación BD
│   └── crear_usuario.sql           # Script creación usuario
└── application.properties          # Configuración de la aplicación
```

## 🧪 Pruebas

### Crear usuarios de prueba

Puedes registrar usuarios desde `/registro` o insertar directamente en la BD:

```sql
-- Usuario ADMIN (password: admin123)
INSERT INTO usuarios (username, email, password, role) VALUES 
('admin', 'admin@skillnest.com', '$2a$10$XYZ...', 'ADMIN');

-- Usuario regular (password: user123)
INSERT INTO usuarios (username, email, password, role) VALUES 
('usuario', 'user@skillnest.com', '$2a$10$ABC...', 'USER');
```

**Nota:** Las contraseñas deben estar encriptadas con BCrypt. Es más fácil usar el formulario de registro.

### Escenarios de prueba

1. **Registro de nuevo usuario**
   - Ir a `/registro`
   - Completar formulario
   - Verificar redirección a login

2. **Login exitoso**
   - Ir a `/login`
   - Ingresar credenciales
   - Verificar acceso a `/home`

3. **Control de acceso por roles**
   - Login como USER: no debería ver opción "Admin" en menú
   - Login como ADMIN: debería ver todas las opciones
   - Intentar acceder a `/admin/detalle` como USER → Error 403

4. **Logout**
   - Click en "Cerrar Sesión"
   - Verificar redirección a `/`

## 🔧 Configuración

### application.properties

```properties
# Puerto del servidor
server.port=8081

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3307/seguridad?useSSL=false&serverTimezone=UTC
spring.datasource.username=seguridad
spring.datasource.password=admin1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Thymeleaf
spring.thymeleaf.cache=false
```

## 📚 Referencias

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Thymeleaf + Spring Security](https://www.thymeleaf.org/doc/articles/springsecurity.html)
- [BCrypt](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)

## 👨‍💻 Autor

Proyecto desarrollado como parte del Bootcamp JavaScript - Módulo 6

## 📄 Licencia

Este es un proyecto educativo de ejemplo.
