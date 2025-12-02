<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle Producto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/panel">Sistema</a>
            <div class="navbar-nav ms-auto">
                <sec:authentication property="name" var="username"/>
                <span class="navbar-text me-3">${username}</span>
                <a href="/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h3>Detalle del Producto</h3>
        <c:choose>
            <c:when test="${not empty producto}">
                <table class="table mt-3">
                    <tr><th>ID:</th><td>${producto.id}</td></tr>
                    <tr><th>Nombre:</th><td>${producto.nombre}</td></tr>
                    <tr><th>Precio:</th><td>$${producto.precio}</td></tr>
                    <tr><th>Descripción:</th><td>${not empty producto.descripcion ? producto.descripcion : 'Sin descripción'}</td></tr>
                </table>
                <div class="mt-3">
                    <a href="/productos" class="btn btn-secondary">Volver</a>
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="/productos/editar/${producto.id}" class="btn btn-warning">Editar</a>
                        <form action="/productos/eliminar/${producto.id}" method="post" style="display: inline;" onsubmit="return confirm('¿Eliminar?');">
                            <button type="submit" class="btn btn-danger">Eliminar</button>
                        </form>
                    </sec:authorize>
                </div>
            </c:when>
            <c:otherwise>
                <p>Producto no encontrado. <a href="/productos">Volver</a></p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>