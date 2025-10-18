<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Encuesta de Satisfacción</title>
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
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #34495e;
        }
        input[type="text"], input[type="number"], select, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
            resize: vertical;
        }
        .radio-group {
            display: flex;
            gap: 20px;
            margin-top: 5px;
        }
        .radio-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .radio-item input[type="radio"] {
            width: auto;
        }
        .submit-btn {
            background-color: #27ae60;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
        }
        .submit-btn:hover {
            background-color: #229954;
        }
        .error {
            background-color: #e74c3c;
            color: white;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #3498db;
            text-decoration: none;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📋 Encuesta de Satisfacción</h1>
        
        <!-- Mostrar error si existe -->
        <c:if test="${not empty error}">
            <div class="error">
                <c:out value="${error}"/>
            </div>
        </c:if>
        
        <form action="procesar-encuesta" method="post">
            <!-- Nombre del usuario -->
            <div class="form-group">
                <label for="nombre">Nombre completo *</label>
                <input type="text" id="nombre" name="nombre" required 
                       placeholder="Ingresa tu nombre completo">
            </div>
            
            <!-- Edad -->
            <div class="form-group">
                <label for="edad">Edad *</label>
                <input type="number" id="edad" name="edad" min="1" max="120" required
                       placeholder="Ingresa tu edad">
            </div>
            
            <!-- ¿Recomendarías este sitio? -->
            <div class="form-group">
                <label>¿Recomendarías este sitio a otros? *</label>
                <div class="radio-group">
                    <div class="radio-item">
                        <input type="radio" id="recomendar_si" name="recomendaria" value="si" required>
                        <label for="recomendar_si">Sí</label>
                    </div>
                    <div class="radio-item">
                        <input type="radio" id="recomendar_no" name="recomendaria" value="no" required>
                        <label for="recomendar_no">No</label>
                    </div>
                </div>
            </div>
            
            <!-- Calificación -->
            <div class="form-group">
                <label for="calificacion">Calificación del sitio (1-5) *</label>
                <select id="calificacion" name="calificacion" required>
                    <option value="">Selecciona una calificación</option>
                    <option value="1">1 - Muy malo</option>
                    <option value="2">2 - Malo</option>
                    <option value="3">3 - Regular</option>
                    <option value="4">4 - Bueno</option>
                    <option value="5">5 - Excelente</option>
                </select>
            </div>
            
            <!-- Comentario -->
            <div class="form-group">
                <label for="comentario">Comentario adicional</label>
                <textarea id="comentario" name="comentario" 
                          placeholder="Comparte tu experiencia o sugerencias (opcional)"></textarea>
            </div>
            
            <!-- Botón enviar -->
            <button type="submit" class="submit-btn">📤 Enviar Encuesta</button>
        </form>
        
        <div class="back-link">
            <a href="index.jsp">⬅️ Volver al inicio</a>
        </div>
    </div>
</body>
</html>