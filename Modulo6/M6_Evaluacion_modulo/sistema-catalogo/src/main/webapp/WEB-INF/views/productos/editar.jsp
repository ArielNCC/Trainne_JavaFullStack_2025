<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Producto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/panel">Sistema</a>
            <div class="navbar-nav ms-auto">
                <sec:authentication property="name" var="username"/>
                <span class="navbar-text me-3">${username} (ADMIN)</span>
                <a href="/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h3>Editar Producto</h3>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        
        <form action="/productos/actualizar" method="post" class="mt-3">
            <input type="hidden" name="id" value="${producto.id}">
            <div class="mb-3">
                <label class="form-label">Nombre *</label>
                <input type="text" class="form-control" name="nombre" value="${producto.nombre}" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Precio *</label>
                <input type="number" class="form-control" name="precio" value="${producto.precio}" step="0.01" min="0" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea class="form-control" name="descripcion" rows="3">${producto.descripcion}</textarea>
            </div>
            <a href="/productos" class="btn btn-secondary">Cancelar</a>
            <button type="submit" class="btn btn-warning">Actualizar</button>
        </form>
    </div>
</body>
</html>