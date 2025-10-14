<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // Simular almacenamiento de productos en sesión
    java.util.List<java.util.Map<String, String>> productos = 
        (java.util.List<java.util.Map<String, String>>) session.getAttribute("productos");
    
    if (productos == null) {
        productos = new java.util.ArrayList<java.util.Map<String, String>>();
        
        // Crear algunos productos de ejemplo para demostración (solo la primera vez)
        java.util.Map<String, String> producto1 = new java.util.HashMap<String, String>();
        producto1.put("nombre", "Smartphone Samsung Galaxy");
        producto1.put("categoria", "electronica");
        producto1.put("precio", "299990");
        producto1.put("enOferta", "true");
        productos.add(producto1);
        
        java.util.Map<String, String> producto2 = new java.util.HashMap<String, String>();
        producto2.put("nombre", "Camiseta de Algodón");
        producto2.put("categoria", "ropa");
        producto2.put("precio", "15990");
        producto2.put("enOferta", "false");
        productos.add(producto2);
        
        java.util.Map<String, String> producto3 = new java.util.HashMap<String, String>();
        producto3.put("nombre", "Maceta Decorativa");
        producto3.put("categoria", "hogar");
        producto3.put("precio", "8990");
        producto3.put("enOferta", "true");
        productos.add(producto3);
        
        session.setAttribute("productos", productos);
    }
    
    // Si se recibieron datos del formulario, agregar el producto
    String nombre = request.getParameter("nombre");
    if (nombre != null && !nombre.trim().isEmpty()) {
        java.util.Map<String, String> producto = new java.util.HashMap<String, String>();
        producto.put("nombre", nombre);
        producto.put("categoria", request.getParameter("categoria"));
        producto.put("precio", request.getParameter("precio"));
        producto.put("enOferta", request.getParameter("enOferta") != null ? "true" : "false");
        productos.add(producto);
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumen del Producto - Tienda en Línea</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 700px;
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
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .product-summary {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
            border-left: 4px solid #28a745;
        }
        .product-detail {
            margin-bottom: 15px;
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            border-bottom: 1px solid #e9ecef;
        }
        .product-detail:last-child {
            border-bottom: none;
        }
        .label {
            font-weight: bold;
            color: #495057;
        }
        .value {
            color: #212529;
        }
        .oferta-badge {
            background-color: #dc3545;
            color: white;
            padding: 5px 10px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
        }
        .categoria-info {
            background-color: #e3f2fd;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 4px solid #2196f3;
        }
        .validation-error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 4px solid #dc3545;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 4px solid #28a745;
        }
        .navigation {
            text-align: center;
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px 10px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        .btn-success:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📋 Resumen del Producto Registrado</h1>
        
        <c:choose>
            <c:when test="${not empty param.nombre}">
                <!-- Validación del precio -->
                <c:set var="precioValido" value="true" />
                <c:if test="${param.precio < 0}">
                    <c:set var="precioValido" value="false" />
                </c:if>
                
                <c:choose>
                    <c:when test="${precioValido}">
                        <div class="success-message">
                            ✅ <strong>¡Producto registrado exitosamente!</strong>
                        </div>
                        
                        <div class="product-summary">
                            <h3>📦 Detalles del Producto</h3>
                            
                            <div class="product-detail">
                                <span class="label">Nombre:</span>
                                <span class="value">
                                    <c:out value="${param.nombre}" />
                                    <c:if test="${param.enOferta == 'true'}">
                                        <span class="oferta-badge">🔥 EN OFERTA</span>
                                    </c:if>
                                </span>
                            </div>
                            
                            <div class="product-detail">
                                <span class="label">Categoría:</span>
                                <span class="value"><c:out value="${param.categoria}" /></span>
                            </div>
                            
                            <div class="product-detail">
                                <span class="label">Precio:</span>
                                <span class="value">$<c:out value="${param.precio}" /> CLP</span>
                            </div>
                            
                            <div class="product-detail">
                                <span class="label">Caracteres en el nombre:</span>
                                <span class="value">${fn:length(param.nombre)} caracteres</span>
                            </div>
                        </div>
                        
                        <!-- Mensaje especial si está en oferta -->
                        <c:if test="${param.enOferta == 'true'}">
                            <div class="success-message">
                                🎉 <strong>¡Excelente!</strong> Este producto está en oferta y será destacado en nuestra tienda.
                            </div>
                        </c:if>
                        
                        <!-- Información según categoría -->
                        <div class="categoria-info">
                            <h4>ℹ️ Información de la Categoría</h4>
                            <c:choose>
                                <c:when test="${param.categoria == 'electronica'}">
                                    <p><strong>Electrónica:</strong> Los productos electrónicos incluyen garantía de 1 año y soporte técnico especializado. Todos los dispositivos son probados antes del envío.</p>
                                </c:when>
                                <c:when test="${param.categoria == 'ropa'}">
                                    <p><strong>Ropa y Accesorios:</strong> Nuestra línea de ropa ofrece las últimas tendencias en moda. Todas las prendas incluyen guía de cuidado y política de cambios hasta 30 días.</p>
                                </c:when>
                                <c:when test="${param.categoria == 'hogar'}">
                                    <p><strong>Hogar y Jardín:</strong> Productos para mejorar tu hogar y jardín. Incluye herramientas, decoración y accesorios de alta calidad con envío especializado.</p>
                                </c:when>
                                <c:otherwise>
                                    <p><strong>Categoría General:</strong> Este producto será clasificado en nuestra sección general hasta que se especifique una categoría más específica.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                    </c:when>
                    <c:otherwise>
                        <div class="validation-error">
                            ❌ <strong>Error de Validación:</strong> El precio no puede ser negativo. Por favor, ingrese un precio válido.
                        </div>
                    </c:otherwise>
                </c:choose>
                
            </c:when>
            <c:otherwise>
                <div class="validation-error">
                    ⚠️ <strong>No se encontraron datos del producto.</strong> Por favor, registre un producto primero.
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="navigation">
            <a href="registro.jsp" class="btn btn-primary">📝 Registrar Otro Producto</a>
            <a href="lista-productos.jsp" class="btn btn-success">📋 Ver Todos los Productos</a>
            <a href="index.jsp" class="btn btn-secondary">🏠 Volver al Inicio</a>
        </div>
    </div>
</body>
</html>