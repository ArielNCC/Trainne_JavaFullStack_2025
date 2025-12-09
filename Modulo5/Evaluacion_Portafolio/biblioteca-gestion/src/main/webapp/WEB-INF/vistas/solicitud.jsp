<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitar Préstamo - Biblioteca Universitaria</title>
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
            max-width: 800px;
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
            font-size: 2em;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: bold;
        }
        
        input[type="text"],
        input[type="email"],
        select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 1em;
            transition: border-color 0.3s;
        }
        
        input:focus,
        select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .required {
            color: #dc3545;
        }
        
        .form-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            margin-top: 30px;
        }
        
        .btn {
            padding: 12px 30px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            font-size: 1em;
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
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .info-box {
            background-color: #e7f3ff;
            border-left: 4px solid #667eea;
            padding: 15px;
            margin-bottom: 25px;
            border-radius: 5px;
        }
        
        .mensaje {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            text-align: center;
        }
        
        .mensaje-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📝 Solicitar Préstamo de Libro</h1>
        <p class="subtitle">Complete el formulario para solicitar un libro</p>
        
        <c:if test="${not empty sessionScope.mensaje}">
            <div class="mensaje mensaje-${sessionScope.tipoMensaje}">
                ${sessionScope.mensaje}
            </div>
            <c:remove var="mensaje" scope="session"/>
            <c:remove var="tipoMensaje" scope="session"/>
        </c:if>
        
        <div class="info-box">
            <p>Por favor, complete todos los campos del formulario. Revisaremos su solicitud y le notificaremos por correo electrónico.</p>
        </div>
        
        <form action="${pageContext.request.contextPath}/solicitudes" method="post" onsubmit="return validarFormulario()">
            <input type="hidden" name="action" value="crear">
            
            <div class="form-group">
                <label for="nombreUsuario">
                    Nombre Completo <span class="required">*</span>
                </label>
                <input type="text" 
                       id="nombreUsuario" 
                       name="nombreUsuario" 
                       placeholder="Ej: Juan Pérez García"
                       required>
            </div>
            
            <div class="form-group">
                <label for="correoUsuario">
                    Correo Electrónico <span class="required">*</span>
                </label>
                <input type="email" 
                       id="correoUsuario" 
                       name="correoUsuario" 
                       placeholder="Ej: juan.perez@universidad.edu"
                       required>
            </div>
            
            <div class="form-group">
                <label for="libroId">
                    Libro a Solicitar <span class="required">*</span>
                </label>
                <select id="libroId" name="libroId" required>
                    <option value="">-- Seleccione un libro --</option>
                    <c:forEach var="libro" items="${libros}">
                        <option value="${libro.id}">
                            ${libro.titulo} - ${libro.autor}
                            <c:if test="${!libro.disponible}"> (No disponible actualmente)</c:if>
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    📤 Enviar Solicitud
                </button>
                <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-secondary">
                    ❌ Cancelar
                </a>
            </div>
        </form>
    </div>
    
    <script>
        function validarFormulario() {
            const nombre = document.getElementById('nombreUsuario').value.trim();
            const correo = document.getElementById('correoUsuario').value.trim();
            const libroId = document.getElementById('libroId').value;
            
            if (!nombre || !correo || !libroId) {
                alert('Por favor, complete todos los campos obligatorios.');
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>
