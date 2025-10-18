<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Respuestas de Encuestas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
        }
        .estadisticas {
            background-color: #ecf0f1;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 25px;
            text-align: center;
        }
        .respuesta-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            margin-bottom: 20px;
            padding: 20px;
            background-color: #fdfdfd;
            transition: box-shadow 0.3s;
        }
        .respuesta-card:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .respuesta-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .nombre-usuario {
            font-size: 18px;
            font-weight: bold;
            color: #2c3e50;
        }
        .calificacion {
            display: flex;
            align-items: center;
            gap: 5px;
            font-weight: bold;
        }
        .calificacion.alta {
            color: #27ae60;
        }
        .calificacion.media {
            color: #f39c12;
        }
        .calificacion.baja {
            color: #e74c3c;
            background-color: #fdf2f2;
            padding: 5px 10px;
            border-radius: 20px;
            border: 1px solid #f5c6cb;
        }
        .datos-usuario {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 15px;
            margin-bottom: 15px;
        }
        .dato-item {
            background-color: #f8f9fa;
            padding: 8px 12px;
            border-radius: 4px;
            font-size: 14px;
        }
        .dato-item strong {
            color: #495057;
        }
        .comentario-seccion {
            background-color: #e7f3ff;
            padding: 12px;
            border-radius: 5px;
            border-left: 4px solid #3498db;
        }
        .comentario-texto {
            font-style: italic;
            color: #333;
            line-height: 1.4;
        }
        .advertencia-menor {
            background-color: #fff3cd;
            color: #856404;
            padding: 8px 12px;
            border-radius: 4px;
            margin-top: 10px;
            border: 1px solid #ffeaa7;
            font-size: 14px;
            text-align: center;
        }
        .alerta-calificacion-baja {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-top: 10px;
            border: 1px solid #f5c6cb;
            text-align: center;
            font-weight: bold;
        }
        .acciones {
            text-align: center;
            margin-top: 30px;
        }
        .acciones a {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        .btn-primary {
            background-color: #3498db;
            color: white;
        }
        .btn-primary:hover {
            background-color: #2980b9;
        }
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
        .sin-respuestas {
            text-align: center;
            color: #666;
            padding: 40px;
            font-style: italic;
        }
        
        @media (max-width: 768px) {
            .datos-usuario {
                grid-template-columns: 1fr;
            }
            .respuesta-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📊 Todas las Respuestas de Encuestas</h1>
        
        <!-- Estadísticas generales -->
        <c:if test="${not empty respuestas}">
            <div class="estadisticas">
                <strong>Total de respuestas recibidas: <c:out value="${fn:length(respuestas)}"/></strong>
            </div>
        </c:if>
        
        <!-- Lista de respuestas usando c:forEach -->
        <c:choose>
            <c:when test="${not empty respuestas}">
                <c:forEach var="respuesta" items="${respuestas}" varStatus="status">
                    <div class="respuesta-card">
                        <!-- Header con nombre y calificación -->
                        <div class="respuesta-header">
                            <div class="nombre-usuario">
                                #<c:out value="${status.count}"/> - <c:out value="${respuesta.nombre}"/>
                            </div>
                            <div class="calificacion ${respuesta.calificacion >= 4 ? 'alta' : (respuesta.calificacion == 3 ? 'media' : 'baja')}">
                                <c:choose>
                                    <c:when test="${respuesta.calificacion >= 4}">⭐</c:when>
                                    <c:when test="${respuesta.calificacion == 3}">⚡</c:when>
                                    <c:otherwise>⚠️</c:otherwise>
                                </c:choose>
                                <c:out value="${respuesta.calificacion}"/>/5
                            </div>
                        </div>
                        
                        <!-- Datos del usuario -->
                        <div class="datos-usuario">
                            <div class="dato-item">
                                <strong>Edad:</strong> <c:out value="${respuesta.edad}"/> años
                            </div>
                            <div class="dato-item">
                                <strong>Recomendaría:</strong> 
                                <c:choose>
                                    <c:when test="${respuesta.recomendaria eq 'si'}">
                                        ✅ Sí
                                    </c:when>
                                    <c:otherwise>
                                        ❌ No
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="dato-item">
                                <strong>Calificación:</strong> <c:out value="${respuesta.calificacion}"/>/5
                            </div>
                        </div>
                        
                        <!-- Comentario -->
                        <c:if test="${not empty respuesta.comentario and fn:trim(respuesta.comentario) ne ''}">
                            <div class="comentario-seccion">
                                <strong>💬 Comentario:</strong>
                                <div class="comentario-texto">
                                    "<c:out value="${respuesta.comentario}"/>"
                                </div>
                                <small style="color: #666;">
                                    (${fn:length(respuesta.comentario)} caracteres)
                                </small>
                            </div>
                        </c:if>
                        
                        <!-- Advertencia si es menor de edad -->
                        <c:if test="${respuesta.edad < 18}">
                            <div class="advertencia-menor">
                                ⚠️ Usuario menor de edad (${respuesta.edad} años)
                            </div>
                        </c:if>
                        
                        <!-- Alerta especial para calificaciones bajas -->
                        <c:if test="${respuesta.calificacion < 3}">
                            <div class="alerta-calificacion-baja">
                                🚨 ATENCIÓN: Calificación baja - Requiere seguimiento
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="sin-respuestas">
                    📭 No hay respuestas de encuestas disponibles en este momento.
                    <br><br>
                    <a href="encuesta.jsp" style="color: #3498db;">¡Sé el primero en completar una encuesta!</a>
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="acciones">
            <a href="encuesta.jsp" class="btn-primary">📝 Nueva Encuesta</a>
            <a href="index.jsp" class="btn-secondary">🏠 Volver al Inicio</a>
        </div>
    </div>
</body>
</html>