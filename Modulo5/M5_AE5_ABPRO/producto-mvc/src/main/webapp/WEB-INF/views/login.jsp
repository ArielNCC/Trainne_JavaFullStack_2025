<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema de Gestión de Productos</title>
</head>
<body>
    <h1>SISTEMA DE GESTIÓN DE PRODUCTOS</h1>
    <h2>Iniciar Sesión</h2>
    
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><strong>Error:</strong> <%= request.getAttribute("error") %></p>
    <% } %>
    
    <form action="/login" method="post">
        <table>
            <tr>
                <td>Usuario:</td>
                <td><input type="text" name="username" required /></td>
            </tr>
            <tr>
                <td>Contraseña:</td>
                <td><input type="password" name="password" required /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="submit">Ingresar</button>
                </td>
            </tr>
        </table>
    </form>
    
    <hr>
    <h3>Usuarios de Prueba:</h3>
    <ul>
        <li>Usuario: admin / Contraseña: admin123</li>
        <li>Usuario: usuario1 / Contraseña: user123</li>
        <li>Usuario: usuario2 / Contraseña: user123</li>
    </ul>
</body>
</html>
