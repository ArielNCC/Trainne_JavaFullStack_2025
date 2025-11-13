<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Productos</title>
</head>
<body>
    <h1>GESTIÓN DE PRODUCTOS</h1>
    <p>Usuario: <strong>${nombreUsuario}</strong> | <a href="/logout">Cerrar Sesión</a></p>
    
    <hr>
    
    <p><a href="/dashboard">Volver al Dashboard</a> | <a href="/productos/nuevo">Nuevo Producto</a></p>
    
    <h2>Lista de Productos</h2>
    
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Categoría</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="producto" items="${productos}">
                <tr>
                    <td>${producto.id}</td>
                    <td>${producto.nombre}</td>
                    <td>${producto.descripcion}</td>
                    <td>$${producto.precio}</td>
                    <td>${producto.stock}</td>
                    <td>${producto.categoria}</td>
                    <td>${producto.activo ? 'Activo' : 'Inactivo'}</td>
                    <td>
                        <a href="/productos/detalle/${producto.id}">Ver</a> |
                        <a href="/productos/editar/${producto.id}">Editar</a> |
                        <a href="/productos/eliminar/${producto.id}" 
                           onclick="return confirm('¿Está seguro de eliminar este producto?')">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty productos}">
                <tr>
                    <td colspan="8">No hay productos registrados</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    
    <hr>
    <p><a href="/dashboard">Volver al Dashboard</a></p>
</body>
</html>
