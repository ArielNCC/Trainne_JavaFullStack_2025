# Seguridad SkillNest

## Descripción

Este proyecto implementa un sistema básico de autenticación y autorización utilizando Spring Boot, Spring Security, y JPA. La aplicación permite gestionar usuarios con roles (ADMIN y USER) y proporciona acceso controlado a diferentes vistas según el rol del usuario.

---

## Configuración del Entorno

### Docker
- **phpMyAdmin**: Disponible en el puerto `8080`.
- **MySQL**: Base de datos expuesta en el puerto `3307`.

### Aplicación
- La aplicación se despliega en el puerto `8081` según la configuración en `application.properties`.

---

## Tecnologías Utilizadas

Las tecnologías principales utilizadas en el proyecto están definidas en el archivo `pom.xml`:
- **Spring Boot 3.5.7**: Framework principal para la aplicación.
- **Spring Security**: Implementación de autenticación y autorización.
- **Spring Data JPA**: Persistencia de datos con Hibernate.
- **Thymeleaf**: Motor de plantillas para las vistas.
- **Bootstrap 5**: Framework CSS para diseño responsivo.
- **MySQL 8.0**: Base de datos relacional.
- **Lombok**: Reducción de código boilerplate.
- **BCrypt**: Encriptación de contraseñas.

---

## Comandos Maven

- **Compilar el proyecto:**
  ```bash
  mvn clean compile
  ```
- **Ejecutar la aplicación:**
  ```bash
  mvn spring-boot:run
  ```
- **Empaquetar la aplicación:**
  ```bash
  mvn clean package
  ```

---

## Acceso a la Aplicación

- **Inicio de sesión:**
  - URL: [http://localhost:8081/login](http://localhost:8081/login)
- **Registro de usuarios:**
  - URL: [http://localhost:8081/registro](http://localhost:8081/registro)
- **Panel de usuario:**
  - URL: [http://localhost:8081/panel](http://localhost:8081/panel)
- **Panel de administración:** (solo para usuarios con rol ADMIN)
  - URL: [http://localhost:8081/admin/detalle](http://localhost:8081/admin/detalle)

---

## Jerarquía de Archivos

```
seguridad_skillnest/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── skillnest/
│   │   │           └── web/
│   │   │               ├── SeguridadApplication.java
│   │   │               ├── ServletInitializer.java
│   │   │               ├── controllers/
│   │   │               │   └── WebController.java
│   │   │               ├── Dto/
│   │   │               │   └── UserDto.java
│   │   │               ├── models/
│   │   │               │   └── Usuario.java
│   │   │               ├── repositories/
│   │   │               │   └── UsuarioRepository.java
│   │   │               ├── security/
│   │   │               │   └── SecurityConfig.java
│   │   │               └── services/
│   │   │                   ├── UserService.java
│   │   │                   └── UsuarioServiceImpl.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── static/
│   │   │   │   ├── crear_base_datos.sql
│   │   │   │   └── crear_usuario.sql
│   │   │   └── templates/
│   │   │       ├── admin.html
│   │   │       ├── login.html
│   │   │       ├── panel.html
│   │   │       ├── perfil.html
│   │   │       └── registro.html
├── pom.xml
└── README.md
```

---

## Funcionamiento de la Autenticación y Autorización

1. **Autenticación:**
   - Los usuarios se autentican mediante un formulario de inicio de sesión en `/login`.
   - Las credenciales se validan contra la base de datos utilizando `UserDetailsService`.

2. **Autorización:**
   - Los usuarios tienen roles asignados (`USER` o `ADMIN`).
   - Las rutas están protegidas según el rol:
     - `/admin/**`: Solo accesible para usuarios con rol `ADMIN`.
     - `/panel`: Accesible para todos los usuarios autenticados.

---

## Encriptación de Contraseñas

- Las contraseñas se encriptan utilizando **BCrypt** antes de almacenarse en la base de datos.
- El bean `PasswordEncoder` en `SecurityConfig` proporciona la implementación de BCrypt.

---

## UML de Clases

```plaintext
+-------------------+
|   Usuario         |
+-------------------+
| - id: Long        |
| - username: String|
| - password: String|
| - role: String    |
| - email: String   |
+-------------------+

+-------------------+
|   UserDto         |
+-------------------+
| - id: Long        |
| - username: String|
| - password: String|
| - role: String    |
| - email: String   |
+-------------------+

+-------------------+
|   UsuarioService  |
+-------------------+
| + saveUser(UserDto)|
| + findByEmail()   |
| + findByUsername()|
| + findAllUsers()  |
+-------------------+

+-------------------+
|   UsuarioServiceImpl |
+-------------------+
| - usuarioRepo        |
| - passwordEncoder    |
| + loadUserByUsername()|
| + saveUser()         |
| + findAllUsers()     |
+-------------------+

+-------------------+
|   UsuarioRepository  |
+-------------------+
| + findByUsername()   |
| + findByEmail()      |
+-------------------+

+-------------------+
|   WebController      |
+-------------------+
| + index()            |
| + login()            |
| + mostrarRegistroForm()|
+-------------------+

+-------------------+
|   SecurityConfig     |
+-------------------+
| + passwordEncoder()  |
| + filterChain()      |
+-------------------+

+-------------------+
|   SeguridadApplication|
+-------------------+
| + main()             |
+-------------------+

+-------------------+
|   ServletInitializer |
+-------------------+
| + configure()        |
+-------------------+
```
