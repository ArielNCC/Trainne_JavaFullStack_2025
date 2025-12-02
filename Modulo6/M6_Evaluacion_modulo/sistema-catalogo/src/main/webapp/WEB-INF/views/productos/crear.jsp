<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Producto</title>
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
        <h3>Crear Producto</h3>
        <form action="/productos/guardar" method="post" class="mt-3">
            <div class="mb-3">
                <label class="form-label">Nombre *</label>
                <input type="text" class="form-control" name="nombre" required placeholder="Ingrese el nombre del producto">
            </div>
            <div class="mb-3">
                <label class="form-label">Precio *</label>
                <input type="number" class="form-control" name="precio" step="0.01" min="0" required placeholder="0.00">
            </div>
            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea class="form-control" name="descripcion" rows="3" placeholder="Descripción del producto (opcional)"></textarea>
            </div>
            <a href="/productos" class="btn btn-secondary">Cancelar</a>
            <button type="submit" class="btn btn-primary">Crear</button>
        </form>
    </div>
</body>
</html>