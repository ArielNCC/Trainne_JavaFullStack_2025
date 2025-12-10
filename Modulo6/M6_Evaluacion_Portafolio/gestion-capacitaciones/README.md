# 📘 Sistema de Gestión de Capacitaciones - SkillNest

## 📋 Descripción del Proyecto
Este proyecto es una aplicación web desarrollada con **Spring Boot** para la gestión de capacitaciones en una empresa. Permite administrar cursos, instructores y empleados, así como gestionar las inscripciones a los cursos disponibles. El sistema cuenta con autenticación y autorización basada en roles (Administrador y Empleado).

---

## 🏗️ Arquitectura del Sistema

### 1. Diagrama de Clases
El siguiente diagrama muestra la estructura de clases del dominio principal y seguridad:

```mermaid
classDiagram
    class Curso {
        +Long id
        +String nombre
        +String descripcion
        +Integer duracionHoras
        +LocalDate fechaInicio
        +LocalDate fechaFin
        +Integer cupoMaximo
        +Boolean activo
    }
    class Empleado {
        +Long id
        +String nombre
        +String apellido
        +String email
        +String departamento
        +String cargo
    }
    class Instructor {
        +Long id
        +String nombre
        +String apellido
        +String email
        +String especialidad
    }
    class Inscripcion {
        +Long id
        +LocalDateTime fechaInscripcion
        +EstadoInscripcion estado
        +String observaciones
    }
    class UserEntity {
        +Long id
        +String username
        +String password
        +String email
        +Boolean enabled
    }
    class RoleEntity {
        +Long id
        +String name
    }

    Curso "1" -- "*" Inscripcion : tiene
    Empleado "1" -- "*" Inscripcion : realiza
    Instructor "1" -- "*" Curso : imparte
    UserEntity "*" -- "*" RoleEntity : posee
    UserEntity .. Empleado : Vinculación lógica (Email)
```

### 2. Diagrama de Base de Datos (ERD)
Estructura relacional de la base de datos MySQL:

```mermaid
erDiagram
    USERS ||--|{ USER_ROLES : tiene
    ROLES ||--|{ USER_ROLES : asignado
    INSTRUCTORES ||--|{ CURSOS : dicta
    EMPLEADOS ||--|{ INSCRIPCIONES : realiza
    CURSOS ||--|{ INSCRIPCIONES : recibe

    USERS {
        bigint id PK
        varchar username
        varchar password
        varchar email
        boolean enabled
    }
    ROLES {
        bigint id PK
        varchar name
    }
    EMPLEADOS {
        bigint id PK
        varchar nombre
        varchar apellido
        varchar email
        varchar departamento
    }
    CURSOS {
        bigint id PK
        varchar nombre
        int duracion_horas
        date fecha_inicio
        date fecha_fin
        bigint instructor_id FK
    }
    INSCRIPCIONES {
        bigint id PK
        bigint empleado_id FK
        bigint curso_id FK
        datetime fecha_inscripcion
        varchar estado
    }
```

---

## 🔄 Flujo de Navegación

### Diagrama de Flujo de Usuario
El sistema dirige a los usuarios a diferentes vistas según su rol:

```mermaid
graph TD
    A[Inicio / Login] --> B{Autenticación}
    B -->|Fallo| A
    B -->|Éxito| C{Rol?}
    
    C -->|ADMIN| D[Panel Admin]
    D --> E[Gestión de Cursos]
    E --> F[Crear Curso]
    E --> G[Editar Curso]
    E --> H[Listar Cursos]
    
    C -->|EMPLEADO| I[Panel Empleado]
    I --> J[Cursos Disponibles]
    J --> K[Inscribirse]
    I --> L[Mis Cursos]
    L --> M[Ver Estado]
    
    C -->|Cualquiera| N[Mi Perfil]
```

---

## 📸 Gestión del Proyecto
Evidencia del uso de herramientas de gestión de proyectos (Trello/Jira/Kanban):

### Tablero de Tareas
![Tablero Kanban](img-project/evidencia1.png)

### Detalle de Historias de Usuario
![Historias de Usuario](img-project/evidencia2.png)

---

## 🚀 Instrucciones de Ejecución
Revisar IniciarProyecto.md

### Requisitos
- Java 21+
- Maven
- MySQL 8.0+

### Pasos Rápidos
1. **Configurar Base de Datos**:
   Asegúrate de tener MySQL corriendo en el puerto configurado (`TuPuertoMysql`) y crea la base de datos:
   ```bash
   mysql -u TuUsuario -p < src/main/resources/static/crear_base_datos.sql
   ```

2. **Ejecutar Aplicación**:
   ```bash
   mvn spring-boot:run
   ```

3. **Acceder**:
   - Abre: `http://localhost:TuPuerto`
   - **Admin**: `admin` / `admin123`
   - **Empleado**: `empleado1` / `empleado123`

---
**Desarrollado para el Módulo 6 - Bootcamp Java Full Stack**
