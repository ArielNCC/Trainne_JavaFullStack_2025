<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Universitaria - Catálogo de Libros</title>
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
            max-width: 1200px;
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
        
        .btn-secondary {
            background-color: #764ba2;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5f3b83;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(118, 75, 162, 0.4);
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
        
        .disponible {
            color: #28a745;
            font-weight: bold;
        }
        
        .no-disponible {
            color: #dc3545;
            font-weight: bold;
        }
        
        .empty-message {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 1.1em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📚 Biblioteca Universitaria</h1>
        <p class="subtitle">Catálogo de Libros Disponibles</p>
        
        <div class="actions">
            <a href="${pageContext.request.contextPath}/solicitudes" class="btn btn-primary">
                📝 Solicitar Préstamo
            </a>
            <a href="${pageContext.request.contextPath}/solicitudes/admin" class="btn btn-secondary">
                🔐 Panel de Administración
            </a>
        </div>
        
        <c:choose>
            <c:when test="${empty libros}">
                <div class="empty-message">
                    <p>📭 No hay libros registrados en el catálogo</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>Autor</th>
                            <th>ISBN</th>
                            <th>Disponibilidad</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="libro" items="${libros}">
                            <tr>
                                <td>${libro.id}</td>
                                <td><strong>${libro.titulo}</strong></td>
                                <td>${libro.autor}</td>
                                <td>${libro.isbn}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${libro.disponible}">
                                            <span class="disponible">✓ Disponible</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="no-disponible">✗ No Disponible</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <p style="margin-top: 20px; text-align: center; color: #666;">
                    <strong>Total de libros:</strong> ${libros.size()}
                </p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
