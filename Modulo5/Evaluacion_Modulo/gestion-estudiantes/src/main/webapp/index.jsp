<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Gestión Estudiantil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
    <style>
        body {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            max-width: 600px;
            text-align: center;
        }
        h2 {
            color: #666;
            font-weight: normal;
            margin-bottom: 30px;
        }
        .info {
            margin-top: 30px;
            padding: 20px;
            background-color: #f0f0f0;
            border-radius: 5px;
            text-align: left;
        }
        .info h3 {
            color: #333;
            margin-bottom: 10px;
        }
        .info ul {
            color: #666;
            line-height: 1.8;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🎓 Sistema de Gestión</h1>
        <h2>Institución Educativa</h2>
        
        <p>Bienvenido al sistema de gestión académica</p>
        
        <a href="${pageContext.request.contextPath}/estudiantes" class="btn btn-primary">
            Acceder a Gestión de Estudiantes
        </a>
    </div>
</body>
</html>
