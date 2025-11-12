<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Productos</title>
</head>
<body>
    <h1>Sistema de Gestión de Productos</h1>
    
    <h2>Menú Principal</h2>
    
    <ul>
        <li><a href="<%= request.getContextPath() %>/registrarProducto">Registrar Nuevo Producto</a></li>
        <li><a href="<%= request.getContextPath() %>/listarProductos">Ver Lista de Productos</a></li>
    </ul>
    
    <hr>
    
    <p>Bienvenido al sistema de gestión de productos.</p>
    <p>Seleccione una opción del menú para comenzar.</p>
</body>
</html>
