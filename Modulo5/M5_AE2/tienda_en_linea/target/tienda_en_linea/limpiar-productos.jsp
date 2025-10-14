<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Limpiar la lista de productos de la sesión
    session.removeAttribute("productos");
    
    // Redirigir de vuelta a la lista de productos
    response.sendRedirect("lista-productos.jsp");
%>