<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración - Biblioteca Universitaria</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            padding: 40px;
        }
        
        h1 {
            color: #667eea;
            text-align: center;
            margin-bottom: 10px;
            font-size: 2.5em;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
            font-size: 1.2em;
        }
        
        .actions {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            margin: 0 10px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background-color: #667eea;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #5568d3;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        thead {
            background-color: #667eea;
            color: white;
        }
        
        th {
            padding: 15px;
            text-align: left;
            font-weight: bold;
        }
        
        td {
            padding: 12px 15px;
            border-bottom: 1px solid #eee;
        }
        
        tbody tr:hover {
            background-color: #f5f5f5;
        }
        
        .estado-pendiente {
            background-color: #fff3cd;
            color: #856404;
            padding: 5px 10px;
            border-radius: 5px;
            font-weight: bold;
            font-size: 0.9em;
        }
        
        .estado-aprobada {
            background-color: #d4edda;
            color: #155724;
            padding: 5px 10px;
            border-radius: 5px;
            font-weight: bold;
            font-size: 0.9em;
        }
        
        .empty-message {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 1.1em;
        }
        
        .stats {
            display: flex;
            justify-content: space-around;
            margin-bottom: 30px;
            gap: 20px;
        }
        
        .stat-card {
            flex: 1;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        
        .stat-number {
            font-size: 2.5em;
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .stat-label {
            font-size: 1em;
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔐 Panel de Administración</h1>
        <p class="subtitle">Gestión de Solicitudes de Préstamo</p>
        
        <div class="actions">
            <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-primary">
                📚 Volver al Catálogo
            </a>
        </div>
        
        <c:if test="${not empty solicitudes}">
            <div class="stats">
                <div class="stat-card">
                    <div class="stat-number">${solicitudes.size()}</div>
                    <div class="stat-label">Total de Solicitudes</div>
                </div>
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty solicitudes}">
                <div class="empty-message">
                    <p>📭 No hay solicitudes registradas</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Usuario</th>
                            <th>Correo</th>
                            <th>Libro Solicitado</th>
                            <th>Autor</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="solicitud" items="${solicitudes}">
                            <tr>
                                <td>${solicitud.id}</td>
                                <td><strong>${solicitud.nombreUsuario}</strong></td>
                                <td>${solicitud.correoUsuario}</td>
                                <td>${solicitud.tituloLibro}</td>
                                <td>${solicitud.autorLibro}</td>
                                <td>
                                    <fmt:formatDate value="${solicitud.fechaSolicitud}" 
                                                   pattern="dd/MM/yyyy HH:mm"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${solicitud.estado == 'PENDIENTE'}">
                                            <span class="estado-pendiente">${solicitud.estado}</span>
                                        </c:when>
                                        <c:when test="${solicitud.estado == 'APROBADA'}">
                                            <span class="estado-aprobada">${solicitud.estado}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span>${solicitud.estado}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
