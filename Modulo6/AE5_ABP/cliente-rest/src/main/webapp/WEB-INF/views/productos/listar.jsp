<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Productos</title>
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
        <h2 class="mb-3">Productos</h2>
        
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
        
        <c:choose>
            <c:when test="${not empty lista_productos}">
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Descripción</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="producto" items="${lista_productos}">
                            <tr>
                                <td>${producto.id}</td>
                                <td>${producto.nombre}</td>
                                <td>$${producto.precio}</td>
                                <td>${producto.descripcion}</td>
                                <td>
                                    <a href="/productos/detalle/${producto.id}" class="btn btn-sm btn-info">Ver</a>
                                    <sec:authorize access="hasRole('ADMIN')">
                                        <a href="/productos/editar/${producto.id}" class="btn btn-sm btn-warning">Editar</a>
                                        <form action="/productos/eliminar/${producto.id}" method="post" style="display: inline;" onsubmit="return confirm('¿Está seguro de eliminar este producto?');">
                                            <button type="submit" class="btn btn-sm btn-danger">Eliminar</button>
                                        </form>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    <p>No hay productos disponibles.</p>
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="/productos/crear" class="btn btn-primary">Crear el primer producto</a>
                    </sec:authorize>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="mt-3">
            <a href="/panel" class="btn btn-secondary">Volver al Panel</a>
        </div>
    </div>
</body>
</html>