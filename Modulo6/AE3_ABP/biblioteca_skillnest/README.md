# Biblioteca-skillnest 📚

Sistema de gestión de inventario para biblioteca desarrollado con Spring Boot, implementando el patrón MVC y utilizando tanto JPA como JDBC para acceso a datos.

## 🎯 Descripción del Proyecto

Aplicación web para una pequeña biblioteca que permite llevar el control de su inventario de libros y autores. El sistema permite consultar, registrar, actualizar y eliminar libros, así como asociar cada libro con un autor.

### Características Principales

- ✅ Gestión completa de **Libros** (CRUD)
- ✅ Gestión completa de **Autores** (CRUD)
- ✅ Control de inventario con cantidad disponible y total
- ✅ Sistema de préstamos y devoluciones
- ✅ Búsqueda por título, ISBN, apellido de autor
- ✅ Implementación dual: **JPA** (Hibernate) y **JDBC** (JdbcTemplate)
- ✅ Transacciones con `@Transactional` para garantizar consistencia
- ✅ Vistas con JSP y Bootstrap 5

## 🛠️ Tecnologías Utilizadas

### Backend
- **Spring Boot 3.5.6**
- **Spring Data JPA** - Repository pattern
- **Spring JDBC** - JdbcTemplate
- **Hibernate** - ORM
- **MySQL 8.0** - Base de datos
- **Java 21**
- **Maven** - Gestión de dependencias

### Frontend
- **JSP** (JavaServer Pages)
- **JSTL** (JSP Standard Tag Library)
- **Bootstrap 5.3.8** - Framework CSS
- **Bootstrap Icons** - Iconografía

### Anotaciones y Características JPA Utilizadas
- `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
- `@Column`, `@OneToMany`, `@ManyToOne`
- `@PrePersist`, `@PreUpdate` - Callbacks
- `@Transactional` - Manejo de transacciones
- `@Autowired` - Inyección de dependencias
- Métodos derivados de consulta
- Consultas personalizadas con JPQL

## 📋 Requisitos Previos

- Java JDK 21 o superior
- MySQL 8.0 o superior
- Maven 3.6+
- IDE (Eclipse, IntelliJ IDEA, VS Code)
- MySQL Workbench (opcional, para gestión de BD)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <url>
cd gestion_productos-main
```

### 2. Configurar la Base de Datos

#### Opción A: Usando Docker (Recomendado)
Puedes usar un `docker-compose.yml` configurado como en los otros proyectos de éste Bootcamp:

```bash
docker-compose up -d
```

Esto iniciará:
- MySQL en puerto 3307
- phpMyAdmin en puerto 8080

#### Opción B: MySQL Local

**Paso 1:** Ejecutar el script de creación de usuario
Abrir MySQL Workbench y ejecutar el archivo:
```
src/main/resources/static/01_crear_usuario.sql
```

**Paso 2:** Ejecutar el script de creación de base de datos
```
src/main/resources/static/02_crear_base_datos.sql
```

Este script:
- Crea la base de datos `biblioteca`
- Crea las tablas `autores` y `libros`
- Inserta datos de ejemplo

### 3. Configurar application.properties

El archivo `src/main/resources/application.properties` ya está configurado:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/biblioteca
spring.datasource.username=Biblioteca_admin
spring.datasource.password=admin1234
```

**Nota:** Si usas mi archivo docker-compose.yml, verifica el cambio en el puerto a 3307:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/biblioteca
```

### 4. Compilar y ejecutar

```bash
# Compilar el proyecto
mvn clean compile
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/skillnest/web/
│   │   ├── BibliotecaSkillnestApplication.java  # Clase principal
│   │   ├── ServletInitializer.java
│   │   ├── controllers/                         # Controladores MVC
│   │   │   ├── AutorController.java
│   │   │   ├── LibroController.java
│   │   │   └── InicioController.java
│   │   ├── models/                              # Entidades JPA y DTOs
│   │   │   ├── Autor.java
│   │   │   ├── Libro.java
│   │   │   ├── AutorDTO.java
│   │   │   └── LibroDTO.java
│   │   ├── repositories/                        # Capa de datos
│   │   │   ├── AutorRepository.java            # JPA Repository
│   │   │   ├── LibroRepository.java            # JPA Repository
│   │   │   ├── AutorDao.java                   # JDBC DAO
│   │   │   └── LibroDao.java                   # JDBC DAO
│   │   ├── rowmappers/                         # RowMappers para JDBC
│   │   │   ├── AutorRowMapper.java
│   │   │   └── LibroRowMapper.java
│   │   └── services/                           # Lógica de negocio
│   │       ├── AutorService.java
│   │       └── LibroService.java
│   ├── resources/
│   │   ├── application.properties              # Configuración
│   │   └── static/                             # Scripts SQL
│   │       ├── 01_crear_usuario.sql
│   │       ├── 02_crear_base_datos.sql
│   │       └── script1.sql
│   └── webapp/WEB-INF/views/                   # Vistas JSP
│       ├── inicio.jsp
│       ├── autores/
│       │   ├── lista-autores.jsp
│       │   ├── crear.jsp
│       │   ├── editar.jsp
│       │   └── detalle.jsp
│       └── libros/
│           ├── lista-libros.jsp
│           ├── crear.jsp
│           ├── editar.jsp
│           └── detalle.jsp
└── test/
    └── java/ TransactionalOperationsTest.java  # Tests que verifica la creación de un autor con un libro y un autor con muchos libros.
```

## 🔄 Flujo de Operaciones

### Modelo MVC Implementado

1. **Vista (JSP)** → Usuario interactúa con formularios
2. **Controlador** → Recibe peticiones HTTP y procesa datos
3. **Servicio** → Contiene lógica de negocio y maneja transacciones
4. **Repository/DAO** → Accede a la base de datos (JPA o JDBC)
5. **Modelo** → Entidades que representan datos

### Doble Implementación: JPA vs JDBC

El proyecto implementa ambas tecnologías para fines educativos:

#### JPA (Usado en la interfaz web)
- `AutorRepository extends JpaRepository`
- `LibroRepository extends JpaRepository`
- Métodos derivados: `findByApellidoContainingIgnoreCase`
- Consultas JPQL personalizadas
- Gestión automática de transacciones

#### JDBC (Disponible para uso alternativo)
- `AutorDao` con `JdbcTemplate`
- `LibroDao` con `JdbcTemplate`
- SQL nativo explícito
- RowMappers personalizados
- Control manual de consultas

## 📊 Modelo de Datos

### Tabla: autores
```sql
CREATE TABLE autores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(100),
    fecha_nacimiento DATE,
    biografia TEXT,
    created_at DATETIME,
    updated_at DATETIME
);
```

### Tabla: libros
```sql
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
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);
```

### Relación
- **Autor** 1:N **Libro** (Un autor puede tener muchos libros)

## 🌐 Endpoints Principales

### Página Principal
- `GET /` - Página de inicio con estadísticas

### Autores
- `GET /autores` - Listar todos los autores
- `GET /autores/crear` - Formulario nuevo autor
- `POST /autores/guardar` - Guardar autor
- `GET /autores/detalle/{id}` - Ver detalles
- `GET /autores/editar/{id}` - Formulario editar
- `POST /autores/actualizar` - Actualizar autor
- `GET /autores/eliminar/{id}` - Eliminar autor
- `GET /autores/buscar?apellido=xxx` - Buscar por apellido

### Libros
- `GET /libros` - Listar todos los libros
- `GET /libros/crear` - Formulario nuevo libro
- `POST /libros/guardar` - Guardar libro
- `GET /libros/detalle/{id}` - Ver detalles
- `GET /libros/editar/{id}` - Formulario editar
- `POST /libros/actualizar` - Actualizar libro
- `GET /libros/eliminar/{id}` - Eliminar libro
- `GET /libros/buscar?titulo=xxx` - Buscar por título
- `GET /libros/disponibles` - Solo libros disponibles
- `POST /libros/prestar/{id}` - Prestar libro
- `POST /libros/devolver/{id}` - Devolver libro

## 🧪 Testing

Revisar en `src/test/java`. 
Para ejecutarlos:

```bash
mvn test
```

## 💾 Datos de Ejemplo

El script `02_crear_base_datos.sql` inserta datos de ejemplo:

### Autores
- Gabriel García Márquez (Colombiano)
- Isabel Allende (Chilena)
- Jorge Luis Borges (Argentino)
- Pablo Neruda (Chileno)
- Mario Vargas Llosa (Peruano)

### Libros
- Cien años de soledad
- El amor en los tiempos del cólera
- La casa de los espíritus
- Ficciones
- El Aleph
- Y más...

## 🔧 Configuración Avanzada

### Cambiar Puerto del Servidor
Editar `application.properties` segun a conveniencia:
```properties
server.port=8081
```

### Habilitar SQL Logging
Ya está habilitado en el proyecto:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Configurar Pool de Conexiones
Agregar en `application.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

## 📝 Notas Importantes

1. **Modificar `docker-compose.yml`** - Según los requisitos de tu equipo o proyecto
2. **Usuario de BD:** `Biblioteca_admin` con contraseña `admin1234`
3. **Base de datos:** `biblioteca` (no `gestion_productos`)
4. El sistema usa `@Transactional` para garantizar la consistencia en operaciones críticas
5. Los métodos de servicio tienen versiones tanto para JPA como JDBC

## 👥 Créditos

Desarrollado como proyecto educativo para bootcamp de Java
- **Organización:** SkillNest
- **Framework:** Spring Boot
- **Patrón:** MVC (Model-View-Controller)

## 📄 Licencia

Proyecto educativo - Uso libre para aprendizaje

---

**Biblioteca-skillnest** © 2024 - Sistema de Gestión de Inventario para Biblioteca
