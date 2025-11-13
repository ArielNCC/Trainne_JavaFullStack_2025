<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${producto.id == null ? 'Nuevo Producto' : 'Editar Producto'}</title>
</head>
<body>
    <h1>${producto.id == null ? 'NUEVO PRODUCTO' : 'EDITAR PRODUCTO'}</h1>
    <p>Usuario: <strong>${nombreUsuario}</strong> | <a href="/logout">Cerrar Sesión</a></p>
    
    <hr>
    
    <p><a href="/productos">Volver a la Lista</a> | <a href="/dashboard">Dashboard</a></p>
    
    <h2>Formulario de Producto</h2>
    
    <form action="${producto.id == null ? '/productos/guardar' : '/productos/actualizar'}" method="post">
        <c:if test="${producto.id != null}">
            <input type="hidden" name="id" value="${producto.id}" />
        </c:if>
        
        <table>
            <tr>
                <td>Nombre:</td>
                <td><input type="text" name="nombre" value="${producto.nombre}" required size="50" /></td>
            </tr>
            <tr>
                <td>Descripción:</td>
                <td><textarea name="descripcion" rows="4" cols="50">${producto.descripcion}</textarea></td>
            </tr>
            <tr>
                <td>Precio:</td>
                <td><input type="number" name="precio" value="${producto.precio}" step="0.01" min="0" required /></td>
            </tr>
            <tr>
                <td>Stock:</td>
                <td><input type="number" name="stock" value="${producto.stock}" min="0" required /></td>
            </tr>
            <tr>
                <td>Categoría:</td>
                <td><input type="text" name="categoria" value="${producto.categoria}" size="30" /></td>
            </tr>
            <c:if test="${producto.id != null}">
                <tr>
                    <td>Estado:</td>
                    <td>
                        <input type="radio" name="activo" value="true" ${producto.activo ? 'checked' : ''} /> Activo
                        <input type="radio" name="activo" value="false" ${!producto.activo ? 'checked' : ''} /> Inactivo
                    </td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2">
                    <button type="submit">${producto.id == null ? 'Guardar' : 'Actualizar'}</button>
                    <button type="button" onclick="window.location.href='/productos'">Cancelar</button>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
