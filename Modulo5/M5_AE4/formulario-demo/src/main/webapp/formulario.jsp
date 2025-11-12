<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Producto</title>
</head>
<body>
    <h1>Registrar Nuevo Producto</h1>
    
    <% 
    String error = (String) request.getAttribute("error");
    if (error != null) {
    %>
        <p style="color: red;"><strong>Error:</strong> <%= error %></p>
    <% } %>
    
    <form action="<%= request.getContextPath() %>/registrarProducto" method="post">
        <fieldset>
            <legend>Datos del Producto</legend>
            
            <label for="nombre">Nombre:</label><br>
            <input type="text" id="nombre" name="nombre" required><br><br>
            
            <label for="precio">Precio:</label><br>
            <input type="number" id="precio" name="precio" step="0.01" min="0.01" required><br><br>
            
            <label for="descripcion">Descripción:</label><br>
            <textarea id="descripcion" name="descripcion" rows="4" cols="50"></textarea><br><br>
            
            <button type="submit">Registrar Producto</button>
            <button type="reset">Limpiar Formulario</button>
        </fieldset>
    </form>
    
    <br>
    <a href="<%= request.getContextPath() %>/listarProductos">Ver Lista de Productos</a> |
    <a href="<%= request.getContextPath() %>/">Volver al Menú Principal</a>
</body>
</html>
