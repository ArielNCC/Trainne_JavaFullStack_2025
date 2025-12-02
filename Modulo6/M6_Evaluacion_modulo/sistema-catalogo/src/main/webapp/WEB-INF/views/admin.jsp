<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel Administrativo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">Sistema</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">Admin: ${usuario.nombreUsuario}</span>
                <a href="/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h2>Panel Administrativo</h2>
        <div class="alert alert-warning">
            <strong>Solo Administradores:</strong> Tienes acceso completo al sistema.
        </div>
        
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Gestión de Productos</h5>
                        <p class="card-text">Crear, editar y eliminar productos</p>
                        <a href="/productos" class="btn btn-primary">Gestionar</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">🔧 API Tester</h5>
                        <p class="card-text">Probar endpoints REST (Mini Postman)</p>
                        <a href="/api-tester" class="btn btn-success">Abrir Tester</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Panel General</h5>
                        <p class="card-text">Volver al panel principal</p>
                        <a href="/panel" class="btn btn-secondary">Panel</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>