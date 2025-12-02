<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sistema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Sistema de Gestión</h1>
        <div class="mt-4">
            <sec:authorize access="isAuthenticated()">
                <p>Ya tienes una sesión activa.</p>
                <a href="/panel" class="btn btn-primary me-3">Ir al Panel</a>
                <a href="/logout" class="btn btn-secondary">Cerrar Sesión</a>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <p>Bienvenido al sistema. Por favor inicia sesión.</p>
                <a href="/login" class="btn btn-primary">Iniciar Sesión</a>
            </sec:authorize>
        </div>
    </div>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cliente REST JWT - Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .api-card {
            transition: transform 0.3s;
        }
        .api-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Header -->
            <div class="col-12 bg-primary text-white py-4">
                <div class="container">
                    <h1 class="display-4 text-center">🚀 Cliente REST con JWT</h1>
                    <p class="lead text-center">Demostración de consumo de APIs protegidas</p>
                </div>
            </div>
        </div>
        
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-6 mb-4">
                    <div class="card api-card h-100 border-success">
                        <div class="card-header bg-success text-white">
                            <h4 class="mb-0">🎯 API REST Endpoints</h4>
                        </div>
                        <div class="card-body">
                            <p>Gestión de productos a través de una interfaz web:</p>
                            <div class="d-grid gap-2">
                                <a href="/productos/listar" class="btn btn-outline-success">
                                    📋 Ver Lista de Productos
                                </a>
                                <a href="/productos/crear" class="btn btn-outline-primary">
                                    ➕ Crear Nuevo Producto
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6 mb-4">
                    <div class="card api-card h-100 border-info">
                        <div class="card-header bg-info text-white">
                            <h4 class="mb-0">🔐 Autenticación JWT</h4>
                        </div>
                        <div class="card-body">
                            <p>El cliente REST se ejecuta automáticamente al iniciar la aplicación y demuestra:</p>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">✅ Autenticación con JWT</li>
                                <li class="list-group-item">✅ Consumo de endpoints protegidos</li>
                                <li class="list-group-item">✅ Manejo de errores de autenticación</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row mt-4">
                <div class="col-12">
                    <div class="card border-warning">
                        <div class="card-header bg-warning">
                            <h4 class="mb-0">🧪 Endpoints de Prueba</h4>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <h6>🔓 Endpoints Públicos:</h6>
                                    <ul>
                                        <li><code>POST /auth/login</code></li>
                                    </ul>
                                </div>
                                <div class="col-md-4">
                                    <h6>🔐 Endpoints Protegidos:</h6>
                                    <ul>
                                        <li><code>GET /usuarios/perfil</code></li>
                                        <li><code>GET /api/productos/listar</code></li>
                                    </ul>
                                </div>
                                <div class="col-md-4">
                                    <h6>👤 Usuarios de Prueba:</h6>
                                    <ul>
                                        <li><strong>admin</strong> / admin123</li>
                                        <li><strong>usuario1</strong> / usuario123</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row mt-4">
                <div class="col-12">
                    <div class="alert alert-info">
                        <h5>💡 Información:</h5>
                        <p class="mb-0">
                            La demostración del cliente REST se ejecuta automáticamente al iniciar la aplicación. 
                            Revisa la consola de la aplicación para ver los resultados de las pruebas de autenticación 
                            y consumo de endpoints protegidos.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p class="mb-0">Cliente REST JWT Demo - Módulo 6 AE5</p>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>