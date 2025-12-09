# Caracterización del Entorno J2EE

## ¿Qué es Java EE y qué tecnologías lo componen?

**Java Enterprise Edition (Java EE)**, ahora conocido como **Jakarta EE**, es una plataforma de desarrollo para crear aplicaciones empresariales robustas, escalables y seguras en Java. Se construye sobre Java SE (Standard Edition) y proporciona un conjunto de especificaciones y APIs para desarrollo empresarial.

### Tecnologías principales de Java EE:

1. **Servlets**
   - Componentes Java que manejan peticiones y respuestas HTTP
   - Se ejecutan en el servidor (servidor de aplicaciones)
   - Base para la mayoría de frameworks web Java

2. **JSP (JavaServer Pages)**
   - Permite crear contenido web dinámico
   - Combina HTML con código Java
   - Se compilan en Servlets automáticamente

3. **JSTL (JavaServer Pages Standard Tag Library)**
   - Biblioteca de etiquetas para simplificar JSP
   - Evita escribir código Java directamente en las páginas
   - Incluye etiquetas para: iteración, condicionales, formateo, acceso a datos

4. **JDBC (Java Database Connectivity)**
   - API para conectar y ejecutar consultas en bases de datos
   - Proporciona métodos para queries SQL
   - Gestiona conexiones, statements y resultados

5. **EJB (Enterprise JavaBeans)**
   - Componentes para lógica de negocio
   - No utilizados en este proyecto (arquitectura más ligera)

6. **JPA (Java Persistence API)**
   - Framework para mapeo objeto-relacional (ORM)
   - Alternativa moderna a JDBC directo
   - No utilizado en este proyecto (usamos JDBC puro)

7. **CDI (Contexts and Dependency Injection)**
   - Sistema de inyección de dependencias
   - No utilizado en este proyecto

---

## ¿Cuál es el rol de JSP, Servlets y DAO en una arquitectura J2EE?

### 📄 **JSP (Vista - View)**

**Rol**: Presentación de datos al usuario

- **Responsabilidad**: Generar la interfaz de usuario (HTML dinámico)
- **Características**:
  - Recibe datos del Servlet a través de `request.setAttribute()`
  - Usa JSTL para mostrar datos sin lógica compleja
  - No debe contener lógica de negocio
  
**Ejemplo en nuestro proyecto:**
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach var="libro" items="${libros}">
    <tr>
        <td>${libro.titulo}</td>
        <td>${libro.autor}</td>
    </tr>
</c:forEach>
```

### ⚙️ **Servlets (Controlador - Controller)**

**Rol**: Controlador central que coordina el flujo de la aplicación

- **Responsabilidad**: 
  - Recibir peticiones HTTP (GET, POST, PUT, DELETE)
  - Procesar datos de entrada (parámetros del formulario)
  - Invocar la capa de negocio (DAOs)
  - Preparar datos para la vista
  - Redirigir o reenviar a la vista apropiada

**Ejemplo en nuestro proyecto:**
```java
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    // Obtener datos usando DAO
    List<Libro> libros = libroDAO.readAll();
    
    // Preparar datos para la vista
    request.setAttribute("libros", libros);
    
    // Reenviar a la vista JSP
    request.getRequestDispatcher("/index.jsp").forward(request, response);
}
```

### 🗄️ **DAO (Modelo - Model - Capa de Acceso a Datos)**

**Rol**: Abstracción de la persistencia de datos

- **Responsabilidad**:
  - Encapsular el acceso a la base de datos
  - Ejecutar operaciones CRUD (Create, Read, Update, Delete)
  - Mapear datos entre objetos Java y tablas de BD
  - Aislar la lógica de negocio de los detalles de persistencia

**Patrón DAO (Data Access Object)**:
```
┌─────────────┐
│  Servlet    │ ← Controlador
└──────┬──────┘
       │ usa
       ▼
┌─────────────┐
│ LibroDAO    │ ← Capa de acceso a datos
└──────┬──────┘
       │ accede
       ▼
┌─────────────┐
│  Base de    │
│   Datos     │
└─────────────┘
```

**Ejemplo en nuestro proyecto:**
```java
public class LibroDAO implements ILibroDAO {
    private Connection conexion;
    
    @Override
    public List<Libro> readAll() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Libro libro = new Libro(
                    resultSet.getInt("id"),
                    resultSet.getString("titulo"),
                    resultSet.getString("autor"),
                    resultSet.getString("isbn"),
                    resultSet.getBoolean("disponible")
                );
                libros.add(libro);
            }
        } catch (SQLException e) {
            // Manejo de errores
        }
        
        return libros;
    }
}
```

---

## ¿Qué ventajas ofrece Java EE frente a otras tecnologías de desarrollo web?

### ✅ **Ventajas de Java EE**

#### 1. **Arquitectura Empresarial Robusta**
- Diseñada específicamente para aplicaciones empresariales grandes
- Soporte nativo para transacciones, seguridad y escalabilidad
- Estándares industriales establecidos y probados

#### 2. **Separación de Responsabilidades (MVC)**
- **Modelo**: Lógica de datos (DAOs, entidades)
- **Vista**: Presentación (JSP)
- **Controlador**: Lógica de control (Servlets)
- Facilita el mantenimiento y testing

#### 3. **Portabilidad**
- Aplicaciones Java EE pueden ejecutarse en múltiples servidores:
  - Apache Tomcat
  - WildFly (JBoss)
  - GlassFish
  - WebLogic
  - WebSphere
- Estándares abiertos (no vendor lock-in)

#### 4. **Escalabilidad**
- Soporte para clustering y balanceo de carga
- Gestión de sesiones distribuidas
- Pool de conexiones a BD
- Manejo eficiente de recursos

#### 5. **Seguridad Integrada**
- Autenticación y autorización declarativa
- Encriptación de comunicaciones (HTTPS)
- Protección contra ataques comunes (CSRF, XSS, SQL Injection)
- Gestión de sesiones segura

#### 6. **Ecosistema Maduro**
- Amplia documentación y comunidad
- Gran cantidad de librerías y frameworks complementarios
- Herramientas de desarrollo robustas (Eclipse, IntelliJ, NetBeans)
- Soporte empresarial disponible

#### 7. **Performance**
- JVM optimizada para alto rendimiento
- Compilación JIT (Just-In-Time)
- Gestión automática de memoria (Garbage Collection)
- Capacidad de manejar alta concurrencia

#### 8. **Integración Empresarial**
- Conectividad con sistemas legacy
- Soporte para web services (SOAP, REST)
- Mensajería asíncrona (JMS)
- Integración con ERPs y sistemas empresariales

---

## Comparación con otras tecnologías

| Aspecto | Java EE | PHP | Node.js | .NET |
|---------|---------|-----|---------|------|
| **Tipado** | Fuerte y estático | Débil y dinámico | Débil y dinámico | Fuerte y estático |
| **Performance** | Alta | Media | Alta | Alta |
| **Escalabilidad** | Excelente | Media | Buena | Excelente |
| **Curva de aprendizaje** | Alta | Baja | Media | Media-Alta |
| **Ecosistema empresarial** | Muy maduro | Medio | Creciente | Muy maduro |
| **Costos de hosting** | Medio-Alto | Bajo | Bajo | Medio |
| **Portabilidad** | Alta | Alta | Alta | Media (Windows preferido) |

---

## Arquitectura de nuestra aplicación

```
┌─────────────────────────────────────────────────────────────┐
│                        NAVEGADOR                             │
│                    (Cliente HTTP)                            │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP Request
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   SERVIDOR TOMCAT                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              CAPA DE VISTA (JSP)                      │  │
│  │   • index.jsp (catálogo)                             │  │
│  │   • solicitud.jsp (formulario)                       │  │
│  │   • confirmacion.jsp                                 │  │
│  │   • admin.jsp                                        │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         CAPA DE CONTROLADOR (Servlets)               │  │
│  │   • CatalogoServlet                                  │  │
│  │   • SolicitudServlet                                 │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │     CAPA DE MODELO (DAOs + Entidades)                │  │
│  │   • LibroDAO → Libro.java                            │  │
│  │   • SolicitudDAO → Solicitud.java                    │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │ JDBC                                 │
└───────────────────────┼──────────────────────────────────────┘
                        ▼
              ┌──────────────────┐
              │  MySQL Database  │
              │  • libros        │
              │  • solicitudes   │
              └──────────────────┘
```

---

## Conclusión

Java EE proporciona una plataforma completa y robusta para el desarrollo de aplicaciones empresariales. Aunque tiene una curva de aprendizaje más pronunciada que algunas alternativas, sus ventajas en términos de:

- ✅ Escalabilidad
- ✅ Seguridad
- ✅ Mantenibilidad
- ✅ Estándares abiertos
- ✅ Ecosistema maduro

Lo convierten en una excelente opción para proyectos empresariales de mediano a gran tamaño que requieren robustez, rendimiento y capacidad de crecimiento a largo plazo.

---

**Proyecto**: Biblioteca Universitaria - Sistema de Gestión  
**Tecnologías**: Java EE, JSP, Servlets, JDBC, MySQL, Apache Tomcat  
**Patrón**: MVC (Model-View-Controller)  
**Fecha**: Diciembre 2025
