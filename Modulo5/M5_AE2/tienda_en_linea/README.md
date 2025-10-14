# 🛒 Tienda en Línea - Sistema de Gestión de Productos

Para correr el proyecto
Compilar:
mvn clean compile
Desplegar:
mvn tomcat7:run
Abrir en navegador:
http://localhost:8080/tienda_en_linea
Detener el programa:
ctrl+c

## 📋 Descripción del Proyecto

Este proyecto es una aplicación web desarrollada con **JSP y JSTL** para la gestión de productos de una tienda en línea. La aplicación permite registrar productos, visualizar detalles y mantener una lista de todos los productos registrados utilizando únicamente tecnologías del lado del servidor.

## 🏗️ Estructura del Proyecto

```
tienda_en_linea/
├── pom.xml                          # Configuración de Maven
├── README.md                        # Documentación del proyecto
├── src/
│   └── main/
│       └── webapp/
│           ├── index.jsp            # Página principal
│           ├── registro.jsp         # Formulario de registro de productos
│           ├── resumen.jsp          # Vista de resultados del producto registrado
│           ├── lista-productos.jsp  # Lista completa de productos
│           ├── limpiar-productos.jsp # Utilidad para limpiar la lista
│           └── WEB-INF/
│               └── web.xml          # Configuración de la aplicación web
└── target/                          # Archivos generados por Maven
    └── tienda_en_linea.war         # Archivo WAR para despliegue
```

## ✨ Características Implementadas

### 📝 **Formulario de Registro (`registro.jsp`)**
- Campo de texto para nombre del producto
- Menú desplegable con 3 categorías:
  - 🔌 Electrónica
  - 👕 Ropa y Accesorios
  - 🏠 Hogar y Jardín
- Campo numérico para precio (con validación)
- Checkbox para indicar si está en oferta
- Envío por método POST a `resumen.jsp`

### 📋 **Vista de Resultados (`resumen.jsp`)**
- **`c:out`** para mostrar valores del formulario de forma segura
- **`c:if`** para mostrar mensaje especial si el producto está en oferta
- **`c:choose/c:when/c:otherwise`** para mostrar texto según la categoría seleccionada
- **`fn:length`** para mostrar cantidad de caracteres del nombre del producto
- **Validación de precio negativo** con estructura condicional
- **Almacenamiento en sesión** para simular persistencia de datos

### 📊 **Lista de Productos (`lista-productos.jsp`)**
- **`c:forEach`** para iterar sobre la lista de productos
- **Productos de ejemplo** precargados para demostración
- **Limpiar Lista** elimina productos agregados por el usuario, dejando los de ejemplo
- **Estadísticas dinámicas**:
  - Total de productos registrados
  - Cantidad de productos en oferta
  - Precio promedio de los productos
- **Diseño responsivo** con CSS Grid

## 🛠️ Tecnologías Utilizadas

- **Java 11**
- **JSP (JavaServer Pages)**
- **JSTL (JSP Standard Tag Library)**
- **Maven** para gestión de dependencias
- **HTML5 & CSS3** para la interfaz de usuario
- **Apache Tomcat** como servidor de aplicaciones

## 📦 Dependencias Maven

```xml
<!-- JSP API -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>

<!-- JSTL -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>

<!-- JSP API -->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2</version>
    <scope>provided</scope>
</dependency>
```

## 🚀 Instrucciones de Instalación y Ejecución

### Prerrequisitos
- **Java 11** o superior instalado
- **Apache Maven 3.6+** instalado
- **Apache Tomcat 9+** instalado (opcional, se puede usar el plugin de Maven)

### Método 1: Usar el Plugin de Tomcat (Recomendado para desarrollo)

1. **Clonar o descargar el proyecto**
   ```bash
   # Si tienes git
   git clone [URL_DEL_REPOSITORIO]
   cd tienda_en_linea
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar con Tomcat embebido**
   ```bash
   mvn tomcat7:run
   ```

4. **Acceder a la aplicación**
   - Abrir navegador web
   - Ir a: `http://localhost:8080/tienda_en_linea`

### Método 2: Despliegue en Tomcat Standalone

1. **Generar el archivo WAR**
   ```bash
   mvn clean package
   ```

2. **Copiar el WAR a Tomcat**
   ```bash
   cp target/tienda_en_linea.war $TOMCAT_HOME/webapps/
   ```

3. **Iniciar Tomcat**
   ```bash
   $TOMCAT_HOME/bin/startup.sh    # Linux/Mac
   $TOMCAT_HOME/bin/startup.bat   # Windows
   ```

4. **Acceder a la aplicación**
   - Ir a: `http://localhost:8080/tienda_en_linea`


## 🔧 Comandos Maven Útiles

```bash
# Limpiar el proyecto
mvn clean

# Compilar
mvn compile

# Ejecutar tests (si los hay)
mvn test

# Generar WAR
mvn package

# Limpiar y generar WAR
mvn clean package

# Ejecutar con Tomcat
mvn tomcat7:run

# Ver dependencias
mvn dependency:tree
```

## 🐛 Solución de Problemas

### Error de Puerto Ocupado
Si el puerto 8080 está ocupado:
```bash
# Cambiar puerto en pom.xml o usar:
mvn tomcat7:run -Dmaven.tomcat.port=8081
```

### Problemas de Encoding
- El proyecto está configurado para UTF-8
- Si hay problemas con acentos, verificar la configuración del servidor

### Errores de Compilación
```bash
# Limpiar y recompilar
mvn clean compile

## 📝 Notas Técnicas

- **Gestión de Estado**: Los productos se almacenan en la sesión HTTP
- **Persistencia**: Los datos se mantienen mientras la sesión esté activa
- **Validaciones**: Realizadas tanto en cliente (HTML5) como en servidor (JSTL)
- **Seguridad**: Uso de `c:out` para prevenir XSS
