# 📚 Biblioteca Universitaria - Sistema de Gestión

Sistema web dinámico desarrollado con **Java EE**, **JSP**, **Servlets**, **JDBC** y **MySQL** para gestionar el catálogo de libros y solicitudes de préstamo de una biblioteca universitaria. Implementa arquitectura en capas y patrón **MVC** (Modelo-Vista-Controlador).

## 🎯 Características Principales

- **Catálogo de Libros**: Visualización de libros disponibles con información detallada (título, autor, ISBN, disponibilidad)
- **Solicitudes de Préstamo**: Formulario para solicitar libros con validación de datos
- **Panel de Administración**: Vista de todas las solicitudes registradas con sus estados
- **Arquitectura MVC**: Separación clara entre Modelo, Vista y Controlador
- **Patrón DAO**: Capa de acceso a datos con JDBC para operaciones CRUD
- **Responsive Design**: Interfaz adaptable a diferentes dispositivos

## 🏗️ Arquitectura del Proyecto

```
biblioteca-gestion/
├── src/
│   └── main/
│       ├── java/
│       │   └── cl/duoc/bibliotecagestion/
│       │       ├── controller/         # Capa Controlador (Servlets)
│       │       │   ├── CatalogoServlet.java
│       │       │   └── SolicitudServlet.java
│       │       ├── dao/                # Capa de Acceso a Datos
│       │       │   ├── ConexionBD.java
│       │       │   ├── ILibroDAO.java
│       │       │   ├── LibroDAO.java
│       │       │   ├── ISolicitudDAO.java
│       │       │   └── SolicitudDAO.java
│       │       ├── filter/             # Filtros
│       │       │   └── CharacterEncodingFilter.java
│       │       └── model/              # Capa Modelo (Entidades)
│       │           ├── Libro.java
│       │           └── Solicitud.java
│       ├── resources/
│       │   ├── database.properties     # Configuración BD
│       │   └── database/
│       │       └── crear_bd.sql        # Script SQL
│       └── webapp/
│           ├── index.jsp               # Catálogo de libros
│           └── WEB-INF/
│               ├── web.xml             # Configuración servlets
│               └── vistas/
│                   ├── solicitud.jsp   # Formulario solicitud
│                   ├── confirmacion.jsp # Confirmación
│                   └── admin.jsp       # Panel admin
├── pom.xml                             # Maven configuration
└── README.md
```

## 🛠️ Tecnologías Utilizadas

- **Java 21** (JDK)
- **Jakarta EE** (Servlets 6.0, JSP 3.1)
- **JSTL 3.0** (Java Standard Tag Library)
- **MySQL 8.0** (Base de datos)
- **Apache Maven** (Gestión de dependencias)
- **Apache Tomcat 10.1+** (Servidor de aplicaciones)
- **JDBC** (Conectividad con BD)

## ⚙️ Requisitos Previos

Antes de compilar y ejecutar la aplicación, asegúrese de tener instalado:

1. **JDK 21**: [Descargar aquí](https://www.oracle.com/java/technologies/downloads/)
2. **Apache Maven**: [Descargar aquí](https://maven.apache.org/download.cgi)
3. **MySQL 8.0+**: [Descargar aquí](https://dev.mysql.com/downloads/mysql/)
4. **Apache Tomcat 10.1+**: [Descargar aquí](https://tomcat.apache.org/download-10.cgi)

## 📦 Instalación y Configuración

### 1️⃣ Clonar o Descargar el Proyecto

```bash
cd biblioteca-gestion
```

### 2️⃣ Configurar la Base de Datos

1. **Iniciar MySQL** (puerto 3307 según configuración actual, o modificar en `database.properties`)

2. **Ejecutar el script SQL** para crear la base de datos y tablas:

```
-- Ver archivo completo en src/main/resources/database/crear_bd.sql
```

3. **Verificar la configuración** en `src/main/resources/database.properties`:

```properties
db.url=jdbc:mysql://localhost:TU_PUERTO/TU_APP?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

**⚠️ IMPORTANTE**: Ajusta el puerto (3307), usuario y contraseña según tu instalación de MySQL.

También debes actualizar `ConexionBD.java` con tus credenciales:

### 3️⃣ Compilar el Proyecto

Desde la raíz del proyecto, ejecutar:

```bash
mvn clean install
```

Este comando:
- Descarga las dependencias necesarias
- Compila el código Java
- Genera el archivo WAR en `target/biblioteca-gestion.war`

### 4️⃣ Configurar Apache Tomcat

1. **Configurar usuarios de Tomcat** (opcional, para despliegue con Maven):

Editar `TOMCAT_HOME/conf/tomcat-users.xml`:

```xml
<tomcat-users>
  <role rolename="manager-script"/>
  <user username="TUADMIN" password="TUCONTRASEÑA" roles="manager-script"/>
</tomcat-users>
```

2. **Configurar Maven** (archivo `~/.m2/settings.xml`):

```xml
<settings>
  <servers>
    <server>
      <id>TomcatServer</id>
      <username>TUADMIN</username>
      <password>TUCONTRASEÑA</password>
    </server>
  </servers>
</settings>
```

## 🚀 Despliegue en Tomcat

### Opción 1: Despliegue Manual

1. Copiar el archivo WAR generado:
```bash
cp target/biblioteca-gestion.war TOMCAT_HOME/webapps/
```

2. Iniciar Tomcat:
```bash
# Windows
TOMCAT_HOME/bin/startup.bat

# Linux/Mac
TOMCAT_HOME/bin/startup.sh
```

3. Acceder a la aplicación:
```
http://localhost:8080/biblioteca-gestion/catalogo
```

### Opción 2: Despliegue con Maven (recomendado)

```bash
mvn tomcat7:deploy
```

Para redesplegar después de cambios:
```bash
mvn tomcat7:redeploy
```

## 🌐 Uso de la Aplicación

### URLs Principales

- **Catálogo de Libros**: `http://localhost:8080/biblioteca-gestion/catalogo`
- **Solicitar Préstamo**: `http://localhost:8080/biblioteca-gestion/solicitudes`
- **Panel Admin**: `http://localhost:8080/biblioteca-gestion/solicitudes/admin`

### Flujo de Usuario

1. **Ver Catálogo**: El usuario accede al catálogo y ve todos los libros disponibles
2. **Solicitar Préstamo**: Hace clic en "Solicitar Préstamo" y completa el formulario (nombre, correo, libro)
3. **Confirmación**: Recibe una página de confirmación con los detalles de su solicitud
4. **Administración**: El administrador puede ver todas las solicitudes desde el panel admin

## 📝 Patrón MVC Implementado

### Modelo (Model)
- `Libro.java`: Representa un libro con sus atributos
- `Solicitud.java`: Representa una solicitud de préstamo
- DAOs: Implementan la lógica de acceso a datos

### Vista (View)
- `index.jsp`: Muestra el catálogo de libros
- `solicitud.jsp`: Formulario de solicitud
- `confirmacion.jsp`: Confirmación de solicitud
- `admin.jsp`: Panel administrativo

### Controlador (Controller)
- `CatalogoServlet`: Maneja GET para mostrar libros
- `SolicitudServlet`: Maneja GET/POST para solicitudes

## 🗄️ Estructura de Base de Datos

### Tabla: libros
```sql
- id (INT, PK, AUTO_INCREMENT)
- titulo (VARCHAR(200))
- autor (VARCHAR(150))
- isbn (VARCHAR(20), UNIQUE)
- disponible (BOOLEAN)
- fecha_registro (TIMESTAMP)
```

### Tabla: solicitudes_prestamo
```sql
- id (INT, PK, AUTO_INCREMENT)
- nombre_usuario (VARCHAR(100))
- correo_usuario (VARCHAR(100))
- libro_id (INT, FK -> libros.id)
- fecha_solicitud (TIMESTAMP)
- estado (VARCHAR(20))
```

## 🔧 Solución de Problemas

### Error de conexión a BD
- Verificar que MySQL esté ejecutándose
- Confirmar puerto, usuario y contraseña en `ConexionBD.java` y `database.properties`
- Verificar que la base de datos `biblioteca_universitaria` exista

### Errores de compilación
- Asegurar que JDK 21 esté instalado y configurado en `JAVA_HOME`
- Ejecutar `mvn clean install -U` para forzar actualización de dependencias

### Página 404 al acceder
- Verificar que Tomcat esté ejecutándose
- Confirmar que el contexto sea correcto: `/biblioteca-gestion`
- Revisar logs de Tomcat en `TOMCAT_HOME/logs/catalina.out`

## 📚 Documentación Adicional

### Caracterización J2EE

**¿Qué es Java EE?**

Java Enterprise Edition (Java EE), ahora Jakarta EE, es una plataforma para desarrollar aplicaciones empresariales en Java. Incluye:
- **Servlets**: Clases Java que manejan peticiones HTTP
- **JSP**: Páginas dinámicas con código Java embebido
- **JSTL**: Biblioteca de tags para JSP
- **JDBC**: API para conexión con bases de datos
- **EJB**: Enterprise JavaBeans (no usado en este proyecto)

**Rol de JSP, Servlets y DAO:**

- **JSP (Vista)**: Presenta la información al usuario con HTML dinámico
- **Servlets (Controlador)**: Procesan peticiones, aplican lógica de negocio y delegan a la vista
- **DAO (Modelo)**: Encapsulan el acceso a la base de datos con CRUD

**Ventajas de Java EE:**

- Arquitectura escalable y robusta
- Separación de responsabilidades (MVC)
- Amplio ecosistema de herramientas y frameworks
- Portabilidad entre servidores de aplicaciones
- Seguridad integrada

## 👥 Autor

Proyecto desarrollado para la evaluación del Módulo 5 - Bootcamp JavaScript

## 📄 Licencia

Este proyecto es de código abierto para fines educativos.
