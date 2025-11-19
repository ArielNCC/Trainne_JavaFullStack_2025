<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">Sistema</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">${usuario.nombreUsuario} (${usuario.rol})</span>
                <a href="/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h2>Mi Perfil</h2>
        <div class="card mt-3">
            <div class="card-header">
                <h5>Información Personal</h5>
            </div>
            <div class="card-body">
                <table class="table">
                    <tr>
                        <th>ID:</th>
                        <td>${usuario.id}</td>
                    </tr>
                    <tr>
                        <th>Usuario:</th>
                        <td>${usuario.nombreUsuario}</td>
                    </tr>
                    <tr>
                        <th>Email:</th>
                        <td>${usuario.email}</td>
                    </tr>
                    <tr>
                        <th>Rol:</th>
                        <td><span class="badge bg-primary">${usuario.rol}</span></td>
                    </tr>
                    <tr>
                        <th>Fecha Creación:</th>
                        <td>${usuario.fechaCreacion}</td>
                    </tr>
                </table>
                <a href="/panel" class="btn btn-secondary">Volver al Panel</a>
            </div>
        </div>
    </div>
</body>
</html>