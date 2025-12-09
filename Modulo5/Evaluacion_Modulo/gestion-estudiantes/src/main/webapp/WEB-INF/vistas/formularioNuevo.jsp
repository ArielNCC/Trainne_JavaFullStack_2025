<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Estudiante</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body>
    <div class="container">
        <h1>➕ REGISTRAR NUEVO ESTUDIANTE</h1>
        
        <div class="header-info">
            <p><strong>Sistema de Gestión Académica</strong></p>
        </div>
        
        <div class="info-box">
            <p>Complete el formulario para registrar un nuevo estudiante en el sistema.</p>
        </div>
        
        <form action="${pageContext.request.contextPath}/estudiantes" method="post" onsubmit="return validarFormulario()">
            <input type="hidden" name="action" value="crear">
            
            <div class="form-group">
                <label for="nombreCompleto">
                    Nombre Completo <span class="required">*</span>
                </label>
                <input type="text" 
                       id="nombreCompleto" 
                       name="nombreCompleto" 
                       placeholder="Ej: Juan Pérez García"
                       required>
            </div>
            
            <div class="form-group">
                <label for="correoElectronico">
                    Correo Electrónico <span class="required">*</span>
                </label>
                <input type="email" 
                       id="correoElectronico" 
                       name="correoElectronico" 
                       placeholder="Ej: juan.perez@universidad.edu"
                       required>
            </div>
            
            <div class="form-group">
                <label for="carrera">
                    Carrera <span class="required">*</span>
                </label>
                <input type="text" 
                       id="carrera" 
                       name="carrera" 
                       placeholder="Ej: Ingeniería en Sistemas"
                       required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    💾 Registrar Estudiante
                </button>
                <a href="${pageContext.request.contextPath}/estudiantes" class="btn btn-secondary">
                    ❌ Cancelar
                </a>
            </div>
        </form>
    </div>
    
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>
