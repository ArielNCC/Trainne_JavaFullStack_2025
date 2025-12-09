<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Sistema de Gestión</title>
</head>
<body>
    <h1>DASHBOARD - SISTEMA DE GESTIÓN</h1>
    <p>Bienvenido, <strong><%= request.getAttribute("nombreUsuario") %></strong></p>
    
    <hr>
    
    <h2>Menú Principal</h2>
    <ul>
        <li><a href="/productos">Gestión de Productos</a> - Ver, crear, editar y eliminar productos</li>
        <li><a href="/logout">Cerrar Sesión</a></li>
    </ul>
    
    <hr>
    
    <h3>Opciones Disponibles:</h3>
    <table border="1" cellpadding="10">
        <tr>
            <th>Módulo</th>
            <th>Descripción</th>
            <th>Acciones</th>
        </tr>
        <tr>
            <td>Productos</td>
            <td>Administrar el catálogo de productos de la tienda</td>
            <td>
                <a href="/productos">Ver Lista</a> |
                <a href="/productos/nuevo">Nuevo Producto</a>
            </td>
        </tr>
    </table>
</body>
</html>
