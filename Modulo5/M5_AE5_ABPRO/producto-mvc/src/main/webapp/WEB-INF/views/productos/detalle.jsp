<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle del Producto</title>
</head>
<body>
    <h1>DETALLE DEL PRODUCTO</h1>
    <p>Usuario: <strong>${nombreUsuario}</strong> | <a href="/logout">Cerrar Sesión</a></p>
    
    <hr>
    
    <p><a href="/productos">Volver a la Lista</a> | <a href="/dashboard">Dashboard</a></p>
    
    <h2>Información del Producto</h2>
    
    <table border="1" cellpadding="10">
        <tr>
            <th>Campo</th>
            <th>Valor</th>
        </tr>
        <tr>
            <td><strong>ID:</strong></td>
            <td>${producto.id}</td>
        </tr>
        <tr>
            <td><strong>Nombre:</strong></td>
            <td>${producto.nombre}</td>
        </tr>
        <tr>
            <td><strong>Descripción:</strong></td>
            <td>${producto.descripcion}</td>
        </tr>
        <tr>
            <td><strong>Precio:</strong></td>
            <td>$${producto.precio}</td>
        </tr>
        <tr>
            <td><strong>Stock:</strong></td>
            <td>${producto.stock} unidades</td>
        </tr>
        <tr>
            <td><strong>Categoría:</strong></td>
            <td>${producto.categoria}</td>
        </tr>
        <tr>
            <td><strong>Estado:</strong></td>
            <td>${producto.activo ? 'Activo' : 'Inactivo'}</td>
        </tr>
        <tr>
            <td><strong>Fecha de Creación:</strong></td>
            <td>${producto.fechaCreacion}</td>
        </tr>
        <tr>
            <td><strong>Última Modificación:</strong></td>
            <td>${producto.fechaModificacion}</td>
        </tr>
    </table>
    
    <hr>
    
    <h3>Acciones</h3>
    <p>
        <a href="/productos/editar/${producto.id}">Editar Producto</a> |
        <a href="/productos/eliminar/${producto.id}" 
           onclick="return confirm('¿Está seguro de eliminar este producto?')">Eliminar Producto</a> |
        <a href="/productos">Volver a la Lista</a>
    </p>
</body>
</html>
