# Agenda de Contactos - Spring Boot

Este proyecto es una agenda de contactos desarrollada con Spring Boot, MySQL y Docker.

## Requisitos
- Docker
- JDK 22 (o compatible)
- Maven 3.9
- MySQL Workbench (opcional, para gestión manual de la base de datos)

## Instrucciones para ejecutar el proyecto

### 1. Levantar el contenedor Docker de MySQL

Asegúrate de tener Docker instalado. Ejecuta el siguiente comando para iniciar el contenedor de MySQL:

```bash
docker run --name mysql-agenda -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=agendacontactos -e MYSQL_USER=agendauser -e MYSQL_PASSWORD=agenda123 -v $(pwd)/init.sql:/docker-entrypoint-initdb.d/init.sql -d mysql:8.0
```

- **Puerto local:** `3307`
- **Usuario:** `agendauser`
- **Contraseña:** `agenda123`
- **Base de datos:** `agendacontactos`

### 2. Crear la tabla `contactos`

Puedes crear la tabla manualmente usando MySQL Workbench o cualquier cliente SQL conectado al contenedor:

```sql
CREATE TABLE contactos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    fecha_creacion DATETIME,
    fecha_actualizacion DATETIME
);
```

> Si usas el archivo `init.sql`, la tabla se crea automáticamente al iniciar el contenedor.

### 3. Compilar el programa

Asegúrate de tener configurado `JAVA_HOME` apuntando a JDK 22 y Maven en tu PATH. Ejecuta:

```bash
mvn clean compile
```

### 4. Ejecutar la aplicación Spring Boot

```bash
mvn spring-boot:run
```

La aplicación estará disponible en [http://localhost:8080](http://localhost:8080)

## Empaquetado como archivo WAR

Para empaquetar la aplicación como un archivo WAR deployable:

### 1. Generar el archivo WAR

```bash
mvn clean package
```

Este comando:
- Ejecuta todas las pruebas unitarias (5 tests)
- Compila el código fuente
- Empaqueta la aplicación como archivo WAR

### 2. Archivo generado

El empaquetado genera dos archivos en el directorio `target/`:

- **`agendacontactos-0.0.1-SNAPSHOT.war`** - Archivo WAR ejecutable (Fat WAR)
- **`agendacontactos-0.0.1-SNAPSHOT.war.original`** - WAR tradicional sin dependencias embebidas

### 3. Opciones de despliegue

#### Opción A: Ejecutar WAR directamente (Servidor embebido)
```bash
java -jar target/agendacontactos-0.0.1-SNAPSHOT.war
```

#### Opción B: Desplegar en servidor de aplicaciones
- El archivo `agendacontactos-0.0.1-SNAPSHOT.war.original` puede desplegarse en:
  - Apache Tomcat
  - WildFly
  - GlassFish
  - Otros servidores compatibles con Jakarta EE


## Solución a problemas de compilación con JDK en VSCode

Si tienes problemas de compilación relacionados con la versión de JDK en Visual Studio Code, puedes solucionarlo fácilmente usando la ventana de ayudante de configuración:

1. Abre la paleta de comandos (`Ctrl+Shift+P`) y busca "Java: Open Proyect Settings" para configurar solo tu proyecto.
2. En la ventana de configuración, selecciona el workspace y define:
   - **Classpath -> JDK runtime:** Elige la versión de JDK que quieres usar (por ejemplo, JDK 21 o JDK 22).
   - **Compiler -> Bytecode version:** Establece la versión de bytecode en 21 para evitar incompatibilidades.
3. Guarda los cambios y reinicia VSCode si es necesario.

## Notas adicionales
- El log de la aplicación se guarda en `app.log`.
- El contenedor MySQL puede ser gestionado con Apache desde Docker Desktop o comandos Docker.
- Si necesitas reiniciar la base de datos, elimina el contenedor y vuelve a crearlo.

---
¡Listo! Ahora puedes gestionar tus contactos desde la web y consultar la base de datos directamente en el contenedor MySQL.
Si tienes dudas me puedes escribir a ni.cavieres@duocuc.cl