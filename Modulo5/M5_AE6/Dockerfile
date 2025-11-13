FROM tomcat:9.0

# Limpiar webapps y copiar el archivo WAR
RUN rm -rf /usr/local/tomcat/webapps/*
COPY MiAplicacion.war /usr/local/tomcat/webapps/

# Exponer el puerto 8080
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
