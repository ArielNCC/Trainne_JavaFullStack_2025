# 🗳️ Sistema de Encuestas Web - J2EE

Una aplicación web completa desarrollada con tecnologías J2EE que permite a los usuarios completar encuestas de satisfacción y visualizar los resultados utilizando JSP, Servlets, JSTL y Apache Tomcat.

## 📋 Características Principales

### ✨ Funcionalidades Implementadas

- **Formulario de Encuesta Interactivo** (`encuesta.jsp`)
  - Campo de nombre del usuario
  - Campo numérico para la edad
  - Radio buttons para recomendación (Sí/No)
  - Select con calificación del 1 al 5
  - Área de texto para comentarios adicionales
  - Validación de campos obligatorios

- **Vista de Resultados Dinámicos** (`resultados.jsp`)
  - Utiliza `c:out` para mostrar cada campo ingresado de forma segura
  - Implementa `c:if` para mostrar agradecimiento especial si calificación ≥ 4
  - Usa `c:choose`, `c:when`, `c:otherwise` para mensajes según recomendación
  - Aplica `fn:length` para mostrar cantidad de caracteres del comentario
  - Muestra advertencia si la edad es menor a 18 años

- **Lista Completa de Respuestas** (`lista_respuestas.jsp`)
  - Carga lista simulada con mínimo 5 respuestas usando `request.setAttribute`
  - Itera sobre respuestas con `c:forEach`
  - Muestra nombre, edad, calificación y comentario de cada usuario
  - Resalta con colores especiales las calificaciones bajas (< 3)
  - Estadísticas generales de respuestas recibidas

## 🛠️ Tecnologías Utilizadas

- **Backend:**
  - Java 11
  - Servlets 4.0
  - JSP 2.3
  - JSTL 1.2
  
- **Frontend:**
  - HTML5
  - CSS3 con diseño responsivo
  - JavaScript (para mejoras de UX)
  
- **Servidor de Aplicaciones:**
  - Apache Tomcat 9.x
  
- **Build Tool:**
  - Maven 3.x

## 📁 Estructura del Proyecto

```
encuesta/
├── pom.xml                              # Configuración Maven
├── src/main/
│   ├── java/com/skillnest/
│   │   ├── model/
│   │   │   └── RespuestaEncuesta.java   # Modelo de datos
│   │   └── servlet/
│   │       ├── EncuestaServlet.java     # Procesa formularios
│   │       └── ListaRespuestasServlet.java # Maneja lista de respuestas
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml                  # Configuración web
│       ├── css/
│       │   └── styles.css               # Estilos personalizados
│       ├── index.jsp                    # Página principal
│       ├── encuesta.jsp                 # Formulario de encuesta
│       ├── resultados.jsp               # Muestra resultados individuales
│       └── lista_respuestas.jsp         # Lista todas las respuestas
└── target/                              # Archivos compilados
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- JDK 11 o superior
- Apache Maven 3.6+
- Apache Tomcat 9.x
- IDE compatible con Java (Eclipse, IntelliJ IDEA, VS Code)

### Pasos de Instalación

1. **Clonar o descargar el proyecto**
   ```bash
   git clone <url-del-proyecto>
   cd encuesta
   ```

2. **Compilar el proyecto con Maven**
   ```bash
   mvn clean compile
   ```

3. **Empaquetar la aplicación**
   ```bash
   mvn clean package
   ```

4. **Ejecutar la aplicación con Maven**
   - En la raíz del proyecto ejecuta:
     ```bash
     mvn tomcat7:run
     ```
   - Accede a: `http://localhost:8080/encuesta`

### Configuración Alternativa con IDE

1. **Importar proyecto Maven** en tu IDE favorito
2. **Configurar servidor Tomcat** en el IDE
3. **Ejecutar directamente** desde el IDE

## 📊 Uso de la Aplicación

### 1. Página Principal (`/`)
- Punto de entrada con navegación principal
- Enlaces a formulario de encuesta y lista de respuestas

### 2. Completar Encuesta (`/encuesta.jsp`)
- Llenar todos los campos obligatorios
- Enviar formulario para procesar datos

### 3. Ver Resultados (`/procesar-encuesta`)
- Muestra confirmación de encuesta recibida
- Utiliza todas las etiquetas JSTL requeridas
- Navegación a otras secciones

### 4. Lista de Respuestas (`/lista-respuestas`)
- Visualiza todas las respuestas simuladas
- Resalta calificaciones bajas
- Muestra estadísticas generales

## 🎯 Características Técnicas JSTL Implementadas

### Core Tags (`c:`)
- ✅ `c:out` - Mostrar datos de forma segura
- ✅ `c:if` - Condicionales simples
- ✅ `c:choose`, `c:when`, `c:otherwise` - Condicionales múltiples
- ✅ `c:forEach` - Iteración sobre colecciones

### Function Tags (`fn:`)
- ✅ `fn:length` - Contar caracteres en strings
- ✅ `fn:trim` - Eliminar espacios en blanco

### Validaciones y Mensajes Especiales
- ✅ Agradecimiento especial para calificaciones ≥ 4
- ✅ Mensajes dinámicos según recomendación
- ✅ Advertencia para menores de 18 años
- ✅ Resaltado de calificaciones bajas (< 3)

## 🎨 Diseño y UX

- **Diseño Responsivo:** Adaptable a móviles y tablets
- **Interfaz Moderna:** Gradientes y efectos visuales atractivos
- **Navegación Intuitiva:** Enlaces claros entre secciones
- **Feedback Visual:** Colores y iconos para diferentes estados
- **Validaciones en Cliente:** JavaScript para mejorar UX

## 🔧 Configuración Avanzada

### Personalizar Respuestas Simuladas
Editar `ListaRespuestasServlet.java` método `crearRespuestasSimuladas()`:

```java
respuestas.add(new RespuestaEncuesta(
    "Nombre Usuario", 
    edad, 
    "si/no", 
    calificacion_1_a_5, 
    "comentario"
));
```

### Modificar Validaciones
En `EncuestaServlet.java` se pueden agregar validaciones adicionales:

```java
// Ejemplo: validar formato de email
if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
    request.setAttribute("error", "Email inválido");
    // ...
}
```

## 🐛 Solución de Problemas

### Error: JSTL no funciona
- Verificar que las dependencias JSTL estén en `pom.xml`
- Comprobar directivas `taglib` en JSP
- Asegurar que Tomcat tenga las librerías JSTL

### Error: 404 al acceder a servlets
- Verificar configuración en `web.xml`
- Comprobar que las clases estén compiladas
- Revisar rutas de URL mapping

### Error: Caracteres especiales
- Verificar encoding UTF-8 en todas las JSP
- Configurar `request.setCharacterEncoding("UTF-8")` en servlets

## 📝 Próximas Mejoras

- [ ] Conexión a base de datos real
- [ ] Persistencia de respuestas
- [ ] Autenticación de usuarios
- [ ] Panel de administración
- [ ] Exportar resultados a Excel/PDF
- [ ] Gráficos estadísticos
- [ ] API REST para integración

## 👥 Contribución

Este proyecto fue desarrollado como parte del aprendizaje de tecnologías J2EE. Las contribuciones son bienvenidas:

1. Fork del proyecto
2. Crear rama para nueva característica
3. Commit de cambios
4. Push a la rama
5. Crear Pull Request

## 📄 Licencia

Este proyecto es de uso educativo y está disponible bajo la licencia MIT.

## 🤝 Soporte

Para dudas o problemas:
- Crear issue en el repositorio
- Contactar al equipo de desarrollo
- Revisar documentación de J2EE/JSP/JSTL

---

**Desarrollado con ❤️ usando Java EE y tecnologías web modernas**