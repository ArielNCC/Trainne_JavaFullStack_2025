<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.skillnest.formulario_demo.model.Producto" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Productos</title>
</head>
<body>
    <h1>Lista de Productos</h1>
    
    <% 
    String mensaje = request.getParameter("mensaje");
    if ("eliminado".equals(mensaje)) {
    %>
        <p style="color: green;"><strong>Producto eliminado exitosamente</strong></p>
    <% } else if ("error".equals(mensaje)) { %>
        <p style="color: red;"><strong>Error al eliminar el producto</strong></p>
    <% } %>
    
    <%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    
    if (productos == null || productos.isEmpty()) {
    %>
        <p>No hay productos registrados en el sistema.</p>
    <% } else { %>
        <table border="1" cellpadding="5" cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Descripción</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% for (Producto producto : productos) { %>
                <tr>
                    <td><%= producto.getId() %></td>
                    <td><%= producto.getNombre() %></td>
                    <td>$<%= String.format("%.2f", producto.getPrecio()) %></td>
                    <td><%= producto.getDescripcion() != null ? producto.getDescripcion() : "Sin descripción" %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/eliminarProducto?id=<%= producto.getId() %>" 
                           onclick="return confirm('¿Está seguro de eliminar este producto?');">
                            Eliminar
                        </a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <p><strong>Total de productos:</strong> <%= productos.size() %></p>
    <% } %>
    
    <br>
    <a href="<%= request.getContextPath() %>/registrarProducto">Registrar Nuevo Producto</a> |
    <a href="<%= request.getContextPath() %>/">Volver al Menú Principal</a>
</body>
</html>
