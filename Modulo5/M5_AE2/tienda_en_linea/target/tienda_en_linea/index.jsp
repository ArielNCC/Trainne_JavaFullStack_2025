<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tienda en Línea</title>
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
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .menu {
            text-align: center;
        }
        .menu a {
            display: inline-block;
            margin: 10px;
            padding: 15px 25px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .menu a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🛒 Tienda en Línea</h1>
        <p style="text-align: center; color: #666; margin-bottom: 30px;">
            Bienvenido a nuestro sistema de gestión de productos
        </p>
        
        <div class="menu">
            <a href="registro.jsp">📝 Registrar Producto</a>
            <a href="lista-productos.jsp">📋 Ver Lista de Productos</a>
        </div>
    </div>
</body>
</html>
