<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Estudiantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body>
    <div class="container">
        <h1>📚 GESTIÓN DE ESTUDIANTES - INSTITUCIÓN EDUCATIVA</h1>
        
        <div class="header-info">
            <p><strong>Sistema de Gestión Académica</strong></p>
        </div>
        
        <!-- Mensaje de éxito o error -->
        <c:if test="${not empty mensaje}">
            <div class="mensaje mensaje-${tipoMensaje}">
                ${mensaje}
            </div>
        </c:if>
        
        <p>
            <a href="${pageContext.request.contextPath}/estudiantes/nuevo" class="btn btn-primary">
                ➕ Registrar Nuevo Estudiante
            </a>
        </p>
        
        <c:choose>
            <c:when test="${empty estudiantes}">
                <div class="empty-message">
                    <p>📭 No hay estudiantes registrados</p>
                    <p>Haga clic en "Registrar Nuevo Estudiante" para agregar el primero</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre Completo</th>
                            <th>Correo Electrónico</th>
                            <th>Carrera</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="estudiante" items="${estudiantes}">
                            <tr>
                                <td>${estudiante.id}</td>
                                <td>${estudiante.nombreCompleto}</td>
                                <td>${estudiante.correoElectronico}</td>
                                <td>${estudiante.carrera}</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/estudiantes/editar/${estudiante.id}" 
                                       class="btn btn-secondary">
                                        ✏️ Editar
                                    </a>
                                    <a href="${pageContext.request.contextPath}/estudiantes/eliminar/${estudiante.id}" 
                                       class="btn btn-danger"
                                       onclick="return confirmarEliminacion('${estudiante.nombreCompleto}')">
                                        🗑️ Eliminar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <p style="margin-top: 15px; text-align: center; color: #666;">
                    <strong>Total de estudiantes:</strong> ${estudiantes.size()}
                </p>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>
