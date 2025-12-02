<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-center">Iniciar Sesión</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${param.error}">
                            <div class="alert alert-danger">Usuario o contraseña incorrectos</div>
                        </c:if>
                        <c:if test="${param.logout}">
                            <div class="alert alert-success">Sesión cerrada correctamente</div>
                        </c:if>
                        
                        <form action="/login" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Usuario:</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña:</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Iniciar Sesión</button>
                        </form>
                        
                        <div class="mt-3">
                            <div class="alert alert-info">
                                <strong>Usuarios de prueba:</strong><br>
                                <strong>ADMIN:</strong> admin / admin123<br>
                                <strong>USER:</strong> usuario1 / usuario123
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>