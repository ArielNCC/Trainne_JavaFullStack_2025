<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel Principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/panel">Sistema</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">Bienvenido, ${usuario.nombreUsuario} (${usuario.rol})</span>
                <a href="/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h2>Panel Principal</h2>
        <p class="text-muted">Bienvenido, <strong>${usuario.nombreUsuario}</strong> | Rol: <strong>${usuario.rol}</strong></p>
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Productos</h5>
                        <p class="card-text">Gestión de productos del sistema</p>
                        <a href="/productos/listar" class="btn btn-primary">Ver Productos</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">🔧 API Tester</h5>
                        <p class="card-text">Probar endpoints REST (Mini Postman)</p>
                        <a href="/api-tester" class="btn btn-success">Abrir Tester</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>