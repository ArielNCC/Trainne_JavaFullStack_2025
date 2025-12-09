<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitud Confirmada - Biblioteca Universitaria</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        .container {
            max-width: 700px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            padding: 40px;
            text-align: center;
        }
        
        .success-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }
        
        h1 {
            color: #28a745;
            margin-bottom: 20px;
            font-size: 2em;
        }
        
        .info-card {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 25px;
            margin: 30px 0;
            text-align: left;
        }
        
        .info-row {
            padding: 12px 0;
            border-bottom: 1px solid #dee2e6;
        }
        
        .info-row:last-child {
            border-bottom: none;
        }
        
        .info-label {
            font-weight: bold;
            color: #667eea;
            display: inline-block;
            width: 150px;
        }
        
        .info-value {
            color: #333;
        }
        
        .mensaje {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 30px;
            margin: 10px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background-color: #667eea;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #5568d3;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-icon">✅</div>
        <h1>¡Solicitud Registrada Exitosamente!</h1>
        
        <div class="mensaje">
            Su solicitud de préstamo ha sido recibida. Le notificaremos por correo electrónico sobre el estado de su solicitud.
        </div>
        
        <div class="info-card">
            <h3 style="color: #667eea; margin-bottom: 15px;">Detalles de su Solicitud</h3>
            
            <div class="info-row">
                <span class="info-label">Nombre:</span>
                <span class="info-value">${solicitud.nombreUsuario}</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Correo:</span>
                <span class="info-value">${solicitud.correoUsuario}</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Libro Solicitado:</span>
                <span class="info-value">${libro.titulo}</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Autor:</span>
                <span class="info-value">${libro.autor}</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Estado:</span>
                <span class="info-value" style="color: #ffc107; font-weight: bold;">PENDIENTE</span>
            </div>
        </div>
        
        <div style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-primary">
                📚 Volver al Catálogo
            </a>
            <a href="${pageContext.request.contextPath}/solicitudes" class="btn btn-secondary">
                📝 Nueva Solicitud
            </a>
        </div>
    </div>
</body>
</html>
