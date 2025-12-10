# 🚀 Iniciar Proyecto - Sistema de Gestión de Capacitaciones

## 📋 Requisitos Previos

- **Java JDK 21** o superior
- **Maven 3.8+**
- **MySQL 8.0+** (servidor en ejecución)
- Puerto **TuPuerto** disponible (o modificar en `application.properties`)

---

## 🗄️ Configurar Base de Datos

### 1. Crear la base de datos

```bash
mysql -u root -p < src/main/resources/static/crear_base_datos.sql
```

O manualmente en MySQL:

```sql
CREATE DATABASE gestion_capacitaciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Ejecutar el script completo

Ejecuta todo el contenido del archivo `src/main/resources/static/crear_base_datos.sql` en MySQL para crear las tablas y datos iniciales.

### 3. Verificar credenciales

Las credenciales de BD están en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:TuPuertoMysql/gestion_capacitaciones
spring.datasource.username=TuUsuario
spring.datasource.password=TuContraseña
```

**Importante:** Asegúrate de que el puerto MySQL (default: 3306, en este proyecto: TuPuertoMysql) coincida con tu configuración.

---

## ▶️ Ejecutar el Proyecto

### Opción 1: Usando Maven

```bash
mvn spring-boot:run
```

### Opción 2: Compilar y ejecutar JAR

```bash
mvn clean package
java -jar target/Seguridad-1.war
```

### Opción 3: Desde IDE (Eclipse/IntelliJ)

1. Importar como proyecto Maven
2. Ejecutar la clase principal: `CapacitacionesApplication.java`

---

## 🌐 Acceder a la Aplicación

Una vez iniciado el servidor, abre tu navegador en:

```
http://localhost:TuPuerto
```

Se redirigirá automáticamente a la página de login.

---

## 👤 Usuarios de Prueba

El script SQL crea usuarios predefinidos:

| Usuario    | Contraseña   | Rol      | Descripción                    |
|-----------|--------------|----------|--------------------------------|
| `admin`   | `admin123`   | ADMIN    | Acceso completo al sistema     |
| `empleado1` | `empleado123` | EMPLEADO | Ver cursos e inscribirse       |
| `empleado2` | `empleado123` | EMPLEADO | Ver cursos e inscribirse       |

**Nota:** Las contraseñas están encriptadas con BCrypt en la base de datos.

---

## 📍 Rutas Principales

### Panel de Administración (requiere rol ADMIN)
- `/admin/cursos` - Gestión de cursos
- `/admin/cursos/crear` - Crear nuevo curso
- `/admin/cursos/editar/{id}` - Editar curso

### Panel de Empleado (requiere rol EMPLEADO)
- `/empleado/cursos` - Ver cursos disponibles
- `/empleado/mis-cursos` - Mis inscripciones

### API REST
- `GET /api/cursos` - Listar todos los cursos
- `GET /api/cursos/disponibles` - Cursos disponibles
- `POST /api/inscripciones` - Inscribirse en un curso

---

## 🛠️ Solución de Problemas

### Error: "Access denied for user 'TuUsuario'@'localhost'"

Verifica que el usuario MySQL existe:

```sql
CREATE USER 'TuUsuario'@'localhost' IDENTIFIED BY 'TuContraseña';
GRANT ALL PRIVILEGES ON gestion_capacitaciones.* TO 'TuUsuario'@'localhost';
FLUSH PRIVILEGES;
```

### Error: "Port TuPuerto already in use"

Cambia el puerto en `application.properties`:

```properties
server.port=8080
```

### Error: "Unable to connect to MySQL"

Verifica que MySQL esté corriendo y el puerto sea correcto (3306 o TuPuertoMysql):

```bash
# Windows
mysql --version

# Linux/Mac
sudo systemctl status mysql
```

---

## 📦 Estructura del Proyecto

```
gestion-capacitaciones/
├── src/main/java/com/empresa/capacitaciones/
│   ├── config/          # Configuración (BD, Seguridad)
│   ├── controller/      # Controladores MVC
│   ├── rest/           # API REST
│   ├── entity/         # Entidades JPA
│   ├── repository/     # Repositorios
│   ├── service/        # Lógica de negocio
│   └── security/       # Autenticación y roles
├── src/main/resources/
│   ├── templates/      # Vistas Thymeleaf
│   ├── static/         # Recursos estáticos
│   └── application.properties
└── pom.xml
```

---

## ✅ Verificar Funcionamiento

1. **Conexión a BD:** Al iniciar, verás en consola:
   ```
   ✓ CONEXIÓN A BASE DE DATOS ESTABLECIDA
   ```

2. **Login exitoso:** Ingresa con usuario `admin` / `admin123`

3. **Ver cursos:** Navega a `/admin/cursos` y verás cursos precargados

---

## 📧 Soporte

Para más información, revisa:
- `INSTRUCCIONES.md` - Documentación completa
- `crear_base_datos.sql` - Script de base de datos

---

**¡Proyecto listo para usar! 🎉**
