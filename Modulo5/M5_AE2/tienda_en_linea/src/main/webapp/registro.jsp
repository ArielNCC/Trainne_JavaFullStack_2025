<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Productos - Tienda en Línea</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
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
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        input[type="checkbox"] {
            margin-right: 10px;
            transform: scale(1.2);
        }
        .checkbox-group {
            display: flex;
            align-items: center;
            margin-top: 5px;
        }
        .btn {
            background-color: #28a745;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
            margin-top: 10px;
        }
        .btn:hover {
            background-color: #218838;
        }
        .btn-secondary {
            background-color: #6c757d;
            margin-top: 10px;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .navigation {
            text-align: center;
            margin-top: 20px;
        }
        .navigation a {
            color: #007bff;
            text-decoration: none;
            margin: 0 10px;
        }
        .navigation a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📝 Registro de Productos</h1>
        
        <form action="resumen.jsp" method="POST">
            <div class="form-group">
                <label for="nombre">Nombre del Producto:</label>
                <input type="text" id="nombre" name="nombre" required 
                       placeholder="Ingrese el nombre del producto">
            </div>
            
            <div class="form-group">
                <label for="categoria">Categoría:</label>
                <select id="categoria" name="categoria" required>
                    <option value="">Seleccione una categoría</option>
                    <option value="electronica">Electrónica</option>
                    <option value="ropa">Ropa y Accesorios</option>
                    <option value="hogar">Hogar y Jardín</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="precio">Precio (CLP):</label>
                <input type="number" id="precio" name="precio" min="0" step="0.01" required 
                       placeholder="0.00">
            </div>
            
            <div class="form-group">
                <label>¿Está en oferta?</label>
                <div class="checkbox-group">
                    <input type="checkbox" id="enOferta" name="enOferta" value="true">
                    <label for="enOferta">Sí, este producto está en oferta</label>
                </div>
            </div>
            
            <button type="submit" class="btn">🚀 Registrar Producto</button>
        </form>
        
        <div class="navigation">
            <a href="index.jsp">🏠 Inicio</a>
            <a href="lista-productos.jsp">📋 Ver Lista de Productos</a>
        </div>
    </div>
</body>
</html>