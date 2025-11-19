# Sistema de Gestión REST API con JWT

**Autor:** Nicolas Ariel  
**Bootcamp:** JavaScript - Módulo 6 - AE5

## Descripción

Mi nombre es **Nicolas Ariel** y este repositorio contiene el desarrollo de una aplicación API REST que gestiona acceso de usuarios por roles, operaciones CRUD con productos e implementa medidas de seguridad robustas mediante autenticación JWT (JSON Web Token) y BCrypt para el cifrado de contraseñas.

La aplicación está construida con **Spring Boot 3.5.7**, utiliza **Spring Security** para control de acceso basado en roles, y proporciona tanto una interfaz web con **JSP** como endpoints REST para integración con aplicaciones externas.

---

## Características Principales

- Autenticación JWT: Token-based authentication para API REST
- Control de acceso por roles: ADMIN y USER con permisos diferenciados
- CRUD completo: Gestión de productos con validación de permisos
- Cifrado BCrypt: Contraseñas hasheadas con BCrypt strength 10
- Interfaz web: Vistas JSP con Bootstrap 5
- API Tester integrado: Mini-Postman para pruebas de endpoints
- Documentación de seguridad: Guías y checklist pre-commit

---

## Requisitos Previos

- **Java JDK**: 21 o superior
- **Maven**: 3.8+ 
- **MySQL**: 8.0+ (puerto 3307 por defecto)
- **Git**: Para clonar el repositorio

---

## 1. Configuración de Base de Datos

### Opción A: Contenedor Docker (Recomendado)

```bash
# Ejecutar MySQL y phpMyAdmin con Docker Compose
docker-compose up -d

# Servicios disponibles:
# - MySQL: localhost:3307
# - phpMyAdmin: http://localhost:8080
```

**Archivo docker-compose.yml:**
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql_rest_api
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: rest_db
      MYSQL_USER: Modulo5
      MYSQL_PASSWORD: modulo5
    ports:
      - "3307:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  phpmyadmin:
    image: phpmyadmin:latest
    container_name: phpmyadmin_rest_api
    environment:
      PMA_HOST: mysql
      PMA_PORT: 3306
    ports:
      - "8080:80"
    depends_on:
      - mysql

volumes:
  mysql_data:
```

### Opción B: MySQL Local

Si tienes MySQL instalado localmente:

1. Asegúrate de que el puerto **3307** esté disponible
2. Ajusta el puerto en el archivo de configuración MySQL (`my.cnf` o `my.ini`):
   ```ini
   [mysqld]
   port=3307
   ```

### Configuración de Credenciales

Edita el archivo `src/main/resources/application.properties`:

```properties
# Puerto del servidor Tomcat (Spring Boot)
server.port=8081

# Configuración de MySQL Database
spring.datasource.url=jdbc:mysql://localhost:3307/rest_db  # ⚙️ CAMBIAR PUERTO AQUÍ
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=Modulo5     # ⚙️ CAMBIAR USUARIO AQUÍ
spring.datasource.password=modulo5     # ⚙️ CAMBIAR CONTRASEÑA AQUÍ
```

**Notas importantes:**
- **Puerto MySQL**: Cambiar `3307` a tu puerto MySQL
- **Usuario**: Cambiar `Modulo5` por tu usuario de base de datos
- **Contraseña**: Cambiar `modulo5` por tu contraseña
- **Base de datos**: El nombre `rest_db` está definido en el script SQL

---

## 2. Creación de Tablas

### Ubicación del Script

El script SQL se encuentra en:
```
📂 src/main/resources/static/
   ├── crear_tablas.sql              ← Script principal
   └── crear_tablas_notebook.mysql-notebook  ← Notebook de MySQL
```

### Ejecutar el Script

**Opción 1: Línea de comandos**
```bash
mysql -u Modulo5 -p -P 3307 -h localhost < src/main/resources/static/crear_tablas.sql
```

**Opción 2: phpMyAdmin**
1. Accede a http://localhost:8080
2. Inicia sesión con las credenciales configuradas
3. Selecciona "SQL" en el menú superior
4. Copia y pega el contenido de `crear_tablas.sql`
5. Ejecuta

**Opción 3: MySQL Workbench**
1. Conecta al servidor MySQL (localhost:3307)
2. Abre el script `crear_tablas.sql`
3. Ejecuta el script completo

### Personalizar Tablas

Si necesitas ajustar las tablas según tus requerimientos, edita el archivo `crear_tablas.sql`:

```sql
-- CAMBIAR NOMBRE DE LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS rest_db  -- ⚙️ Modificar aquí
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- AJUSTAR COLUMNAS DE LA TABLA USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,    -- ⚙️ Cambiar tamaño
    email VARCHAR(100) NOT NULL UNIQUE,             -- ⚙️ Cambiar tamaño
    contrasena VARCHAR(255) NOT NULL,               -- NO cambiar (BCrypt)
    rol VARCHAR(50) NOT NULL DEFAULT 'USER',        -- ⚙️ Añadir más roles
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- AJUSTAR COLUMNAS DE LA TABLA PRODUCTOS
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,                   -- ⚙️ Cambiar tamaño
    precio DECIMAL(10,2) NOT NULL,                  -- ⚙️ Ajustar precisión
    descripcion TEXT                                -- ⚙️ Agregar columnas
) ENGINE=InnoDB;
```

**⚠️ Importante:**
- Si cambias el nombre de la base de datos, actualiza también `application.properties`
- Si modificas nombres de columnas, actualiza las entidades JPA correspondientes en `model/Usuario.java` y `model/Producto.java`
- La columna `contrasena` debe ser VARCHAR(255) para almacenar hashes BCrypt

### Usuarios Iniciales

El script crea dos usuarios de prueba:

| Usuario | Contraseña | Rol | Email |
|---------|-----------|-----|-------|
| `admin` | `admin123` | ADMIN | admin@correo.com |
| `usuario1` | `usuario123` | USER | usuario1@correo.com |

---

## 3. Testing

### Verificar Conexiones de Usuario

Ejecuta los tests de integración para verificar que la autenticación funciona correctamente:

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar solo los tests de autenticación
mvn test -Dtest=AuthLoginIntegrationTest
```

**Tests incluidos:**
- `testLoginAdmin_DebeRetornarTokenValido()`: Verifica login de admin
- `testLoginUsuario1_DebeRetornarTokenValido()`: Verifica login de usuario1
- `testLoginConCredencialesInvalidas_DebeRetornar401()`: Valida rechazo de credenciales incorrectas
- `testLoginUsuarioNoExistente_DebeRetornar401()`: Valida rechazo de usuarios inexistentes

**Salida esperada:**
```
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Generar Hash BCrypt para Contraseñas

Si necesitas actualizar o crear nuevas contraseñas:

**1. Inicia la aplicación:**
```bash
mvn spring-boot:run
```

**2. Realiza una petición POST a `/auth/hash`:**

```bash
# Usando cURL
curl -X POST http://localhost:8081/auth/hash \
  -H "Content-Type: application/json" \
  -d '{"password": "miNuevaContraseña"}'

# Respuesta:
{
  "password": "miNuevaContraseña",
  "hash": "$2a$10$abcd1234..."
}
```

**3. Usando Postman:**
- **URL**: `http://localhost:8081/auth/hash`
- **Método**: POST
- **Headers**: `Content-Type: application/json`
- **Body (raw JSON)**:
  ```json
  {
    "password": "miNuevaContraseña"
  }
  ```

**4. Actualiza la base de datos con el hash generado:**

```sql
UPDATE usuarios 
SET contrasena = '$2a$10$abcd1234...' -- Hash generado
WHERE nombre_usuario = 'usuario1';
```

**⚠️ Nota de seguridad:**
El endpoint `/auth/hash` debe ser **eliminado o protegido** antes del despliegue en producción ya que es solo para desarrollo.

---

## 4. Despliegue en Desarrollo

### Compilar y Ejecutar

```bash
# Limpiar artefactos previos
mvn clean

# Compilar el proyecto
mvn compile

# Instalar dependencias
mvn install

# Ejecutar la aplicación
mvn spring-boot:run
```

**Aplicación disponible en:**
- Interfaz Web: http://localhost:8081
- API REST: http://localhost:8081/api/productos
- Login: http://localhost:8081/login

### Iniciar Sesión como ADMIN

**Credenciales:**
- Usuario: `admin`
- Contraseña: `admin123`

**Operaciones CRUD disponibles:**

| Operación | Endpoint | Descripción |
|-----------|----------|-------------|
| **Listar** | GET `/productos/listar` | Ver todos los productos |
| **Ver detalle** | GET `/productos/detalle/{id}` | Ver un producto específico |
| **Crear** | GET `/productos/crear` → POST `/productos/guardar` | Formulario para crear producto |
| **Editar** | GET `/productos/editar/{id}` → POST `/productos/actualizar` | Formulario para editar producto |
| **Eliminar** | POST `/productos/eliminar/{id}` | Eliminar un producto |

**Interacción con API Tester:**
1. Desde el panel, haz clic en **"Probar API REST"**
2. Se abrirá `/api-tester` con tu token JWT pre-cargado
3. Prueba los endpoints REST:
   - 📋 **GET All Products**: Lista todos los productos
   - 🔍 **GET Product by ID**: Obtiene un producto por su ID
   - ➕ **POST Create Product**: Crea un nuevo producto
   - ✏️ **PUT Update Product**: Actualiza un producto existente
   - ❌ **DELETE Product**: Elimina un producto

### Iniciar Sesión como USER (usuario1)

**Credenciales:**
- Usuario: `usuario1`
- Contraseña: `usuario123`

**Operaciones permitidas:**

| Operación | Endpoint | Descripción |
|-----------|----------|-------------|
| **Listar** | GET `/productos/listar` | Ver todos los productos ✅ |
| **Ver detalle** | GET `/productos/detalle/{id}` | Ver un producto específico ✅ |
| **Crear** | POST `/productos/guardar` | ❌ Solo ADMIN |
| **Editar** | POST `/productos/actualizar` | ❌ Solo ADMIN |
| **Eliminar** | POST `/productos/eliminar/{id}` | ❌ Solo ADMIN |

**Interacción con API Tester:**
1. Accede a `/api-tester` desde el panel
2. El token JWT de usuario1 está pre-cargado
3. Puedes probar endpoints de **solo lectura** (GET):
   - ✅ GET All Products
   - ✅ GET Product by ID
   - ❌ POST/PUT/DELETE generarán error 403 Forbidden

**Diferencias clave:**
- Los botones **Crear**, **Editar** y **Eliminar** no aparecen en la interfaz web
- Las peticiones REST a endpoints de escritura (POST/PUT/DELETE) serán rechazadas con error 403

---

## 5. Desarrollo

### Jerarquía de Archivos

```
📦 cliente-rest
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java/com/skillnest/cliente_rest
│   │   │   ├── 📄 ClienteRestApplication.java        # Punto de entrada
│   │   │   ├── 📄 ServletInitializer.java            # Configuración WAR
│   │   │   ├── 📂 config
│   │   │   │   ├── 📄 PasswordConfig.java            # Bean PasswordEncoder (deprecated)
│   │   │   │   └── 📄 RestTemplateConfig.java        # Configuración RestTemplate
│   │   │   ├── 📂 controller
│   │   │   │   ├── 📄 ApiExternaController.java      # Consumo APIs externas
│   │   │   │   ├── 📄 AuthViewController.java        # Vistas de autenticación
│   │   │   │   ├── 📄 HomeController.java            # Controlador home
│   │   │   │   ├── 📄 ProductoController.java        # CRUD web productos
│   │   │   │   └── 📄 UsuarioController.java         # Endpoints usuarios
│   │   │   ├── 📂 model
│   │   │   │   ├── 📄 Producto.java                  # Entidad JPA Producto
│   │   │   │   ├── 📄 ProductoDto.java               # DTO Producto (legacy)
│   │   │   │   └── 📄 Usuario.java                   # Entidad JPA Usuario
│   │   │   ├── 📂 repository
│   │   │   │   ├── 📄 ProductoRepository.java        # Repository JPA Producto
│   │   │   │   └── 📄 UsuarioRepository.java         # Repository JPA Usuario
│   │   │   ├── 📂 restcontrollers
│   │   │   │   ├── 📄 AuthController.java            # API REST autenticación
│   │   │   │   └── 📄 ProductoRestController.java    # API REST productos
│   │   │   ├── 📂 security
│   │   │   │   ├── 📄 JwtFiltroAutenticacion.java    # Filtro JWT para requests
│   │   │   │   ├── 📄 JwtUtil.java                   # Utilidades JWT
│   │   │   │   └── 📄 SecurityConfig.java            # Configuración Spring Security
│   │   │   └── 📂 service
│   │   │       ├── 📄 DebugService.java              # Debugging autenticación
│   │   │       ├── 📄 ProductoService.java           # Interface servicio Producto
│   │   │       ├── 📄 ProductoServiceImpl.java       # Implementación servicio
│   │   │       └── 📄 UsuarioService.java            # Servicio + UserDetailsService
│   │   ├── 📂 resources
│   │   │   ├── 📄 application.properties              # Configuración Spring Boot
│   │   │   ├── 📄 application.properties.example      # Template seguro
│   │   │   └── 📂 static
│   │   │       ├── 📄 crear_tablas.sql                # Script SQL
│   │   │       ├── 📄 crear_tablas_notebook.mysql-notebook
│   │   │       └── 📄 test.html
│   │   └── 📂 webapp/WEB-INF/views
│   │       ├── 📄 admin.jsp                          # Panel administrador
│   │       ├── 📄 api-tester.jsp                     # Mini-Postman
│   │       ├── 📄 index.jsp                          # Página inicio
│   │       ├── 📄 login.jsp                          # Formulario login
│   │       ├── 📄 panel.jsp                          # Panel usuario
│   │       ├── 📄 perfil.jsp                         # Perfil usuario
│   │       └── 📂 productos
│   │           ├── 📄 crear.jsp                      # Formulario crear
│   │           ├── 📄 detalle.jsp                    # Detalle producto
│   │           ├── 📄 editar.jsp                     # Formulario editar
│   │           └── 📄 listar.jsp                     # Lista productos
│   └── 📂 test/java/com/skillnest/cliente_rest
│       ├── 📄 AuthLoginIntegrationTest.java          # Tests autenticación
│       └── 📄 ClienteRestApplicationTests.java       # Tests aplicación
├── 📄 .gitignore                                     # Exclusiones Git
├── 📄 SEGURIDAD.md                                   # Documentación seguridad
├── 📄 pom.xml                                        # Dependencias Maven
└── 📄 README.md                                      # Este archivo
```

### Responsabilidad de cada Clase Java

#### **Package: root**

| Clase | Responsabilidad |
|-------|----------------|
| `ClienteRestApplication.java` | **Main class**: Punto de entrada de la aplicación Spring Boot. Implementa `CommandLineRunner` para mostrar mensaje de inicio y URL de acceso. |
| `ServletInitializer.java` | **Configuración WAR**: Extiende `SpringBootServletInitializer` para permitir despliegue en servidores Tomcat externos como archivo WAR. |

#### **Package: config**

| Clase | Responsabilidad |
|-------|----------------|
| `PasswordConfig.java` | **Bean PasswordEncoder** (deprecated): Define el bean `BCryptPasswordEncoder`. Ahora se configura en `SecurityConfig.java`. |
| `RestTemplateConfig.java` | **Bean RestTemplate**: Configura `RestTemplate` para consumo de APIs REST externas (no usado actualmente). |

#### **Package: controller**

| Clase | Responsabilidad |
|-------|----------------|
| `ApiExternaController.java` | **Consumo APIs externas**: Controlador para demostrar consumo de APIs REST externas con `RestTemplate` (funcionalidad demo deshabilitada). |
| `AuthViewController.java` | **Vistas de autenticación**: Maneja rutas web para `/login`, `/panel`, `/perfil/usuario`, `/admin/panel` y `/api-tester`. Inyecta información del usuario autenticado en los modelos. |
| `HomeController.java` | **Controlador home**: Redirige la raíz `/` a `/login` o `/panel` según el estado de autenticación. |
| `ProductoController.java` | **CRUD web productos**: Controlador para vistas JSP de productos. Maneja formularios HTML para crear, editar, listar y ver detalles de productos. |
| `UsuarioController.java` | **Endpoints usuario**: Proporciona endpoint REST `/usuarios/perfil` para obtener información del usuario autenticado mediante token JWT. |

#### **Package: model**

| Clase | Responsabilidad |
|-------|----------------|
| `Producto.java` | **Entidad JPA**: Mapea la tabla `productos` de MySQL. Usa `BigDecimal` para precios. Contiene métodos de compatibilidad con `ProductoDto` marcados como `@JsonIgnore`. |
| `ProductoDto.java` | **DTO legacy**: Data Transfer Object usado en implementaciones antiguas. Mantiene compatibilidad con código existente. |
| `Usuario.java` | **Entidad JPA**: Mapea la tabla `usuarios`. Almacena credenciales hasheadas (BCrypt), rol (ADMIN/USER) y metadata de usuario. |

#### **Package: repository**

| Clase | Responsabilidad |
|-------|----------------|
| `ProductoRepository.java` | **Repository JPA Producto**: Interface que extiende `JpaRepository<Producto, Long>`. Spring Data JPA genera automáticamente implementaciones de CRUD. |
| `UsuarioRepository.java` | **Repository JPA Usuario**: Interface con métodos personalizados `findByNombreUsuario()` y `findByEmail()` para búsquedas específicas. |

#### **Package: restcontrollers**

| Clase | Responsabilidad |
|-------|----------------|
| `AuthController.java` | **API REST autenticación**: Endpoint `/auth/login` que valida credenciales y genera tokens JWT. Endpoint temporal `/auth/hash` para generar hashes BCrypt (eliminar en producción). |
| `ProductoRestController.java` | **API REST productos**: Endpoints REST en `/api/productos` para operaciones CRUD (GET, POST, PUT, DELETE). Protegidos con JWT. Solo ADMIN puede modificar datos. |

#### **Package: security**

| Clase | Responsabilidad |
|-------|----------------|
| `JwtFiltroAutenticacion.java` | **Filtro JWT**: Extiende `OncePerRequestFilter`. Intercepta requests HTTP, extrae y valida tokens JWT del header `Authorization: Bearer <token>`. Establece autenticación en `SecurityContext`. |
| `JwtUtil.java` | **Utilidades JWT**: Clase de utilidad para generar tokens JWT, extraer claims (usuario, rol), validar expiración y firmar con clave secreta. |
| `SecurityConfig.java` | **Configuración Spring Security**: Define cadena de filtros de seguridad, URLs públicas (`/login`, `/auth/login`, `/css/**`), URLs protegidas por rol, configura `BCryptPasswordEncoder` y `AuthenticationManager`. |

#### **Package: service**

| Clase | Responsabilidad |
|-------|----------------|
| `DebugService.java` | **Debugging autenticación**: Servicio temporal para diagnosticar problemas de autenticación. Imprime información detallada sobre validación de credenciales y comparación de hashes BCrypt. |
| `ProductoService.java` | **Interface servicio Producto**: Define contrato de métodos de lógica de negocio para productos (CRUD + formateo). |
| `ProductoServiceImpl.java` | **Implementación servicio Producto**: Implementa `ProductoService`. Contiene lógica de negocio y orquesta llamadas a `ProductoRepository`. |
| `UsuarioService.java` | **Servicio Usuario + UserDetailsService**: Implementa `UserDetailsService` de Spring Security para carga de usuarios. Proporciona métodos para buscar usuarios y validar credenciales con BCrypt. |

#### **Package: test**

| Clase | Responsabilidad |
|-------|----------------|
| `AuthLoginIntegrationTest.java` | **Tests autenticación**: Suite de tests de integración usando `@SpringBootTest` y `MockMvc`. Valida login de admin y usuario1, errores 401 para credenciales inválidas, y formato de respuesta JWT. |
| `ClienteRestApplicationTests.java` | **Tests aplicación**: Test básico que verifica que el contexto de Spring Boot se carga correctamente sin errores. |

### Diagrama UML de Clases

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         SPRING BOOT APPLICATION                              │
│                      ClienteRestApplication (main)                           │
└──────────────────────────────────┬──────────────────────────────────────────┘
                                   │
          ┌────────────────────────┼────────────────────────┐
          │                        │                        │
          ▼                        ▼                        ▼
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│   CONTROLLERS    │    │ REST CONTROLLERS │    │     SECURITY     │
├──────────────────┤    ├──────────────────┤    ├──────────────────┤
│ AuthViewController│    │ AuthController   │    │ SecurityConfig   │
│ ProductoController│    │ ProductoRest...  │    │ JwtUtil          │
│ UsuarioController │    │                  │    │ JwtFiltro...     │
│ HomeController    │    │                  │    │                  │
└────────┬─────────┘    └────────┬─────────┘    └────────┬─────────┘
         │                       │                       │
         │ uses                  │ uses                  │ uses
         ▼                       ▼                       ▼
┌──────────────────────────────────────────────────────────────┐
│                         SERVICES                              │
├──────────────────────────────────────────────────────────────┤
│  UsuarioService (implements UserDetailsService)              │
│  ProductoService (interface) ← ProductoServiceImpl           │
│  DebugService                                                 │
└────────┬─────────────────────────────────────────────────────┘
         │ uses
         ▼
┌──────────────────────────────────────────────────────────────┐
│                       REPOSITORIES                            │
├──────────────────────────────────────────────────────────────┤
│  UsuarioRepository extends JpaRepository<Usuario, Long>      │
│  ProductoRepository extends JpaRepository<Producto, Long>    │
└────────┬─────────────────────────────────────────────────────┘
         │ manages
         ▼
┌──────────────────────────────────────────────────────────────┐
│                         MODELS                                │
├──────────────────────────────────────────────────────────────┤
│  Usuario (@Entity)                                            │
│  │ - Long id                                                  │
│  │ - String nombreUsuario                                     │
│  │ - String email                                             │
│  │ - String contrasena (BCrypt hash)                          │
│  │ - String rol (ADMIN/USER)                                  │
│  │ - Date fechaCreacion                                       │
│                                                                │
│  Producto (@Entity)                                           │
│  │ - Long id                                                  │
│  │ - String nombre                                            │
│  │ - BigDecimal precio                                        │
│  │ - String descripcion                                       │
│                                                                │
│  ProductoDto (legacy DTO)                                     │
└──────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    SECURITY FLOW                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  HTTP Request                                                │
│       ↓                                                      │
│  JwtFiltroAutenticacion (OncePerRequestFilter)              │
│       ↓ (validates Bearer token)                            │
│  JwtUtil (extracts username, validates signature)           │
│       ↓                                                      │
│  UsuarioService (loads UserDetails)                         │
│       ↓                                                      │
│  SecurityContext (sets Authentication)                      │
│       ↓                                                      │
│  SecurityConfig (authorizes based on roles)                 │
│       ↓                                                      │
│  Controller (executes business logic)                       │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

**Relaciones clave:**
- **Controllers** → dependen de **Services** para lógica de negocio
- **Services** → dependen de **Repositories** para persistencia
- **Repositories** → manejan **Models** (entidades JPA)
- **Security** → intercepta requests, valida JWT, consulta **UsuarioService**
- **AuthController** → genera JWT usando **JwtUtil**, valida con **UsuarioService**

---

## 6. Maven

### Dependencias Principales (pom.xml)

```xml
<properties>
    <java.version>21</java.version>
</properties>

<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- JWT (JSON Web Token) -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- JSP Support -->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
    </dependency>
    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    </dependency>
    <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    </dependency>

    <!-- Security Tags for JSP -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-taglibs</artifactId>
    </dependency>

    <!-- DevTools (optional, development only) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Resumen de Dependencias

| Dependencia | Propósito |
|-------------|-----------|
| **spring-boot-starter-data-jpa** | ORM Hibernate + Spring Data JPA |
| **spring-boot-starter-security** | Autenticación y autorización |
| **spring-boot-starter-web** | REST API + Spring MVC |
| **mysql-connector-j** | Driver JDBC para MySQL 8+ |
| **jjwt-api, jjwt-impl, jjwt-jackson** | Generación y validación de JWT |
| **tomcat-embed-jasper** | Motor de renderizado JSP |
| **jakarta.servlet.jsp.jstl** | JSTL para vistas JSP |
| **spring-security-taglibs** | Tags de seguridad en JSP (`<sec:authorize>`) |
| **spring-boot-devtools** | Hot reload en desarrollo |
| **spring-boot-starter-test** | JUnit 5 + Mockito + Spring Test |
| **spring-security-test** | Utilidades para testing con Spring Security |

### Requerimientos del Sistema

| Componente | Versión Mínima | Versión Recomendada |
|------------|----------------|---------------------|
| **Java JDK** | 21 | 21 (LTS) |
| **Maven** | 3.6.3 | 3.8+ |
| **MySQL** | 8.0 | 8.0.43+ |
| **Spring Boot** | 3.5.7 | 3.5.7 |
| **Tomcat Embedded** | 10+ (Jakarta EE 9) | Incluido en Spring Boot |

---

## Seguridad

Este proyecto implementa múltiples capas de seguridad:

- **BCrypt**: Hashing de contraseñas con salt automático (strength 10)
- **JWT**: Tokens firmados con HS256 (clave secreta en `application.properties`)
- **Spring Security**: Control de acceso basado en roles (RBAC)
- **CSRF Protection**: Deshabilitado solo para endpoints REST `/api/**`
- **Stateless Sessions**: Para API REST, `SessionCreationPolicy.STATELESS`
- **.gitignore**: Previene commit de credenciales y configuraciones sensibles

**Consulta `SEGURIDAD.md`** para:
- Checklist pre-commit
- Configuración de variables de entorno
- Recomendaciones para producción
- Buenas prácticas de seguridad

---

## Licencia

Este proyecto fue desarrollado con fines educativos para el **Bootcamp de JavaScript - Módulo 6**.

---

## Contacto

**Autor:** Nicolas Ariel  
**Proyecto:** Módulo 6 - AE5 - API REST con JWT

Si tienes dudas o sugerencias sobre el proyecto -> issue

---
