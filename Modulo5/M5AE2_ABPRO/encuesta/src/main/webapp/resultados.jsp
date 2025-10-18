<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultados de la Encuesta</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
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
        .resultado-item {
            margin-bottom: 15px;
            padding: 10px;
            background-color: #ecf0f1;
            border-radius: 5px;
            border-left: 4px solid #3498db;
        }
        .resultado-item strong {
            color: #2c3e50;
        }
        .agradecimiento {
            background-color: #d5f4e6;
            color: #155724;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border: 1px solid #c3e6cb;
            text-align: center;
            font-weight: bold;
        }
        .mensaje-recomendacion {
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
        }
        .recomendacion-positiva {
            background-color: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
        }
        .recomendacion-negativa {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .advertencia-menor {
            background-color: #fff3cd;
            color: #856404;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border: 1px solid #ffeaa7;
            text-align: center;
            font-weight: bold;
        }
        .info-comentario {
            background-color: #e7f3ff;
            color: #004085;
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
            border: 1px solid #b8daff;
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
        .btn-success {
            background-color: #27ae60;
            color: white;
        }
        .btn-success:hover {
            background-color: #229954;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>✅ Encuesta Recibida</h1>
        
        <c:if test="${not empty respuesta}">
            <p style="text-align: center; color: #27ae60; margin-bottom: 25px;">
                <strong>¡Gracias por completar nuestra encuesta!</strong>
            </p>
            
            <!-- Mostrar datos usando c:out -->
            <div class="resultado-item">
                <strong>Nombre:</strong> <c:out value="${respuesta.nombre}"/>
            </div>
            
            <div class="resultado-item">
                <strong>Edad:</strong> <c:out value="${respuesta.edad}"/> años
            </div>
            
            <div class="resultado-item">
                <strong>Calificación:</strong> <c:out value="${respuesta.calificacion}"/>/5
            </div>
            
            <div class="resultado-item">
                <strong>Comentario:</strong> 
                <c:choose>
                    <c:when test="${empty respuesta.comentario or fn:trim(respuesta.comentario) eq ''}">
                        <em>Sin comentarios</em>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${respuesta.comentario}"/>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <!-- Mostrar información del comentario usando fn:length -->
            <c:if test="${not empty respuesta.comentario and fn:trim(respuesta.comentario) ne ''}">
                <div class="info-comentario">
                    📝 Tu comentario tiene <strong><c:out value="${fn:length(respuesta.comentario)}"/></strong> caracteres.
                </div>
            </c:if>
            
            <!-- Agradecimiento especial para calificaciones >= 4 usando c:if -->
            <c:if test="${respuesta.calificacion >= 4}">
                <div class="agradecimiento">
                    🌟 ¡Muchas gracias por tu excelente calificación! Tu opinión nos motiva a seguir mejorando.
                </div>
            </c:if>
            
            <!-- Mensaje según recomendación usando c:choose, c:when, c:otherwise -->
            <c:choose>
                <c:when test="${respuesta.recomendaria eq 'si'}">
                    <div class="mensaje-recomendacion recomendacion-positiva">
                        👍 ¡Genial! Nos alegra saber que recomendarías nuestro sitio a otros usuarios.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mensaje-recomendacion recomendacion-negativa">
                        💭 Entendemos que no recomendarías el sitio. Valoramos tu honestidad y trabajaremos para mejorar.
                    </div>
                </c:otherwise>
            </c:choose>
            
            <!-- Advertencia si es menor de 18 usando c:if -->
            <c:if test="${respuesta.edad < 18}">
                <div class="advertencia-menor">
                    ⚠️ Advertencia: Eres menor de edad. Ten en cuenta que algunos servicios pueden tener restricciones de edad.
                </div>
            </c:if>
        </c:if>
        
        <c:if test="${empty respuesta}">
            <div style="text-align: center; color: #e74c3c; padding: 20px;">
                ❌ No se encontraron datos de la encuesta. Por favor, vuelve a completar el formulario.
            </div>
        </c:if>
        
        <div class="acciones">
            <a href="encuesta.jsp" class="btn-success">📝 Nueva Encuesta</a>
            <a href="lista-respuestas" class="btn-primary">📊 Ver Todas las Respuestas</a>
            <a href="index.jsp" class="btn-secondary">🏠 Volver al Inicio</a>
        </div>
    </div>
</body>
</html>