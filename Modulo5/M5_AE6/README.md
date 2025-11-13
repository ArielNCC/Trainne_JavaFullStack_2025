# Despliegue de una aplicación JEE con Docker y Tomcat

## 1. Uso de Docker para despliegue Tomcat

El proyecto utiliza Docker para levantar un contenedor Tomcat 9 y desplegar automáticamente el archivo `MiAplicacion.war` generado por Maven. La configuración se encuentra en el archivo `Dockerfile` y `docker-compose.yml`.

**Comando principal:**
```bash
# Compilar el proyecto y levantar el contenedor
mvn clean package
cp miaplicacion/target/MiAplicacion.war MiAplicacion.war
# Desde la raíz del proyecto
docker-compose up -d --build
```

## 2. Estructura simple del proyecto `miaplicacion`

```
miaplicacion/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── miapp/
│       │           └── controllers/
│       │               └── UsuarioServlet.java
│       └── webapp/
│           ├── index.jsp
│           └── WEB-INF/
│               └── web.xml
├── pom.xml
```

## 3. Configuración necesaria para `web.xml`

El descriptor de despliegue define el servlet y la página de bienvenida. Cada elemento tiene un propósito específico:

- `<display-name>`: Nombre descriptivo de la aplicación web.
- `<welcome-file-list>`: Define la página que se muestra al acceder a la raíz del contexto (`index.jsp`).
- `<servlet>`: Registra el servlet en la aplicación. Se especifica el nombre y la clase Java que lo implementa.
- `<servlet-mapping>`: Asocia el servlet registrado a una URL específica (`/saludo_servlet`).

Ejemplo:
```xml
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>MiAplicacion</display-name>
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
    <servlet>
        <servlet-name>ControladorUsuario</servlet-name>
        <servlet-class>com.miapp.controllers.UsuarioServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ControladorUsuario</servlet-name>
        <url-pattern>/saludo_servlet</url-pattern>
    </servlet-mapping>
</web-app>
```

## 4. Configuración necesaria del `pom.xml`

El `pom.xml` incluye las dependencias necesarias para servlets y JSP, y configura el empaquetado como WAR. A continuación se explica el propósito de cada dependencia:

- **javax.servlet-api**: Permite crear y ejecutar servlets en el contenedor Tomcat. Se declara como `provided` porque Tomcat ya incluye esta API.
- **javax.servlet.jsp-api**: Habilita el uso de páginas JSP en la aplicación. También se declara como `provided` porque Tomcat la provee.
- **jstl**: Permite el uso de la biblioteca de etiquetas estándar de JSP (JSTL), útil para lógica en páginas JSP.

Ejemplo:
```xml
<dependencies>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
        <!-- Permite crear servlets, Tomcat ya incluye esta API -->
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
        <version>2.3.3</version>
        <scope>provided</scope>
        <!-- Permite usar JSP, Tomcat ya incluye esta API -->
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
        <!-- Permite usar JSTL en JSP -->
    </dependency>
</dependencies>
<build>
    <finalName>MiAplicacion</finalName>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>21</source>
                <target>21</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>3.4.0</version>
            <configuration>
                <failOnMissingWebXml>false</failOnMissingWebXml>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 5. Cómo compilar el proyecto usando Docker Desktop

1. Compila el proyecto con Maven:
   ```bash
   mvn clean package
   ```
2. Copia el archivo WAR generado:
   ```bash
   cp miaplicacion/target/MiAplicacion.war MiAplicacion.war
   ```
3. Levanta el contenedor Tomcat con Docker Desktop:
   ```bash
   docker-compose up -d --build
   ```
4. Accede a la aplicación en:
   - Página principal: [http://localhost:8080/MiAplicacion/](http://localhost:8080/MiAplicacion/)
   - Servlet: [http://localhost:8080/MiAplicacion/saludo_servlet](http://localhost:8080/MiAplicacion/saludo_servlet)

## 6. Evidencia del proyecto compilado

### Página principal
![Página principal](img/index.png)

### Servlet funcionando
![Servlet saludo](img/saludos-servlet.png)
