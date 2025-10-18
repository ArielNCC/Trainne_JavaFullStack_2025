<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Encuestas Web</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
        }
        .menu {
            text-align: center;
            margin-top: 30px;
        }
        .menu a {
            display: inline-block;
            padding: 15px 30px;
            margin: 10px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .menu a:hover {
            background-color: #2980b9;
        }
        .descripcion {
            text-align: center;
            color: #666;
            margin-bottom: 20px;
            line-height: 1.6;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🗳️ Sistema de Encuestas Web</h1>
        <div class="descripcion">
            <p>Bienvenido al sistema de encuestas de nuestro sitio web.</p>
            <p>Tu opinión es importante para nosotros. Comparte tu experiencia y ayúdanos a mejorar.</p>
        </div>
        
        <div class="menu">
            <a href="encuesta.jsp">📝 Llenar Encuesta</a>
            <a href="lista-respuestas">📊 Ver Todas las Respuestas</a>
        </div>
    </div>
</body>
</html>
