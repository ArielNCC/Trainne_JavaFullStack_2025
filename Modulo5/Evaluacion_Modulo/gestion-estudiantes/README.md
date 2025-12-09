# Sistema de Gestión de Estudiantes

Aplicación web Java para gestionar estudiantes utilizando arquitectura MVC.

## Requisitos Previos

- JDK 11 o superior
- Apache Tomcat 9/10/11
- MySQL 8.0
- Maven 3.6+

## Instrucciones de Despliegue

### 1. Base de Datos
Crear o levantar una base de datos MySQL usando Docker, instalación local, o servicios cloud (AWS RDS, Azure Database, etc.).

### 2. Crear Tablas
Ejecutar el script `src/main/resources/database/crear_bd.sql` en tu base de datos MySQL para crear las tablas necesarias.

### 3. Configurar Conexión
Editar las credenciales de conexión en:
- **Archivo Java**: `src/main/java/cl/duoc/gestionestudiantes/dao/ConexionBD.java`
- **Archivo de Propiedades**: `src/main/resources/database.properties`

Actualizar con tu URL, usuario y contraseña de MySQL.

### 4. Configurar Puertos
Si ejecutas en local:
- **MySQL**: Puerto 3306 (o el que hayas configurado)
- **Tomcat**: Puerto 8080 (por defecto) o 8081 si hay conflictos

### 5. Usuario Tomcat
Agregar un usuario administrador en `TOMCAT_HOME/conf/tomcat-users.xml`:
```xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="tu_password" roles="manager-gui,manager-script"/>
```

### 6. Compilar y Generar WAR
```bash
mvn clean package
```

El archivo `.war` se generará en `target/producto-mvc.war`

### 7. Desplegar en Tomcat
- **Opción A**: Copiar `producto-mvc.war` a `TOMCAT_HOME/webapps/`
- **Opción B**: Usar Tomcat Manager en `http://localhost:8080/manager/html`

Esperar a que Tomcat despliegue la aplicación y acceder a:
```
http://localhost:8080/producto-mvc
```

## Tecnologías Utilizadas

- Java 11+
- Servlets & JSP
- MySQL
- Maven
- Bootstrap 5

## Estructura del Proyecto

```
src/main/
├── java/cl/duoc/gestionestudiantes/
│   ├── controller/    # Servlets
│   ├── dao/          # Data Access Objects
│   ├── model/        # Entidades
│   └── service/      # Lógica de negocio
├── resources/
│   └── database/     # Scripts SQL
└── webapp/
    └── WEB-INF/
        └── views/    # Páginas JSP
```

## Autor

Desarrollado como proyecto del Módulo 5 - Bootcamp JavaScript
