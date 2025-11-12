# Sistema de Gestión de Productos

Aplicación web desarrollada con Spring Boot, JSP y MySQL para la gestión CRUD de productos.

## Tecnologías

- **Spring Boot 3.5.7** - Framework base
- **Java 21** - Lenguaje de programación
- **MySQL** - Base de datos
- **JSP** - Vistas web
- **Maven** - Gestión de dependencias
- **Docker** - Contenedor de base de datos

## Configuración y Ejecución

### 1. Base de Datos

#### Iniciar contenedor MySQL:
```bash
docker run -d --name mysql-modulo5 -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root mysql:latest
```

#### Crear base de datos y usuario:
```bash
# Ejecutar scripts
docker exec -i mysql-modulo5 mysql -uroot -proot < src/main/resources/database/crear_db.sql
docker exec -i mysql-modulo5 mysql -uroot -proot < src/main/resources/database/crear_usuario.sql

# Verificar
docker exec -it mysql-modulo5 mysql -uModulo5 -pmodulo5 -e "USE gestion_productos; SELECT * FROM productos;"
```

### 2. Aplicación

#### Compilar y ejecutar:
```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

#### Acceder a:
- **Aplicación**: http://localhost:8081
- **Formulario**: http://localhost:8081/registrarProducto
- **Lista**: http://localhost:8081/listarProductos

## Funcionalidades

✅ **CRUD Completo**: Crear, listar y eliminar productos  
✅ **Patrón DAO**: Separación de lógica de acceso a datos  
✅ **Singleton**: Conexión única a base de datos  
✅ **Servlets**: Manejo de peticiones HTTP  
✅ **JSP**: Vistas dinámicas minimalistas  
✅ **Validaciones**: Campos obligatorios y tipos de datos  

## Estructura del Proyecto

```
src/main/
├── java/com/skillnest/formulario_demo/
│   ├── config/ConexionDB.java          # Singleton de conexión
│   ├── dao/ProductoDAO.java            # Interfaz DAO
│   ├── dao/ProductoDAOImp.java         # Implementación DAO
│   ├── model/Producto.java             # DTO
│   └── servlet/                        # Servlets HTTP
├── resources/
│   ├── application.properties          # Configuración
│   └── database/                       # Scripts SQL
└── webapp/                             # Vistas JSP
```

## Configuración

**Puerto**: 8081 (configurable en `application.properties`)  
**Base de datos**: MySQL en puerto 3307  
**Usuario DB**: Modulo5 / modulo5  

## Solución de Problemas

- **Puerto ocupado**: Cambiar `server.port` en `application.properties`
- **Error de conexión**: Verificar que Docker esté corriendo
- **Compilación**: Asegurar JDK 21 instalado

## 1. Correr y conectarse al contenedor Docker

Crear un nuevo usuario con:
Puerto 3307
user: root
Password: root

Conectarse al usuario Modulo5

## 2. Ejecutar el script
- Abrir MySQL Workbench o la terminal de MySQL
- Copiar y ejecutar el script completo
- Verificar que la tabla se haya creado correctamente y contenga los 8 productos de prueba

---

## 3. Jerarquía Completa de Archivos del Proyecto
```
GestionProductos/
│
├── lib/                                    # Bibliotecas externas (JARs)
│   ├── mysql-connector-java-8.x.x.jar
│   └── javax.servlet-api-4.0.1.jar
│
├── src/
│   └── main/
│       ├── java/
│       │   │
│       │   ├── config/
│       │   │   └── ConexionDB.java         # Singleton para la conexión a BD
│       │   │
│       │   ├── dao/
│       │   │   ├── ProductoDAO.java        # Interfaz con métodos CRUD
│       │   │   └── ProductoDAOImpl.java    # Implementación del DAO
│       │   │
│       │   ├── modelo/
│       │   │   └── Producto.java           # DTO (Data Transfer Object)
│       │   │
│       │   └── servlet/
│       │       ├── RegistrarProductoServlet.java   # Servlet para crear productos
│       │       ├── ListaProductosServlet.java      # Servlet para listar productos
│       │       └── EliminarProductoServlet.java    # Servlet para eliminar productos
│       │
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml                 # Descriptor de despliegue
│           │
│           ├── css/
│           │   └── estilos.css             # Estilos para las vistas
│           │
│           ├── index.jsp                   # Página de inicio
│           ├── formulario.jsp              # Formulario para agregar productos
│           └── listaProductos.jsp          # Vista de tabla de productos
│
└── README.md                               # Documentación del proyecto
```

---
