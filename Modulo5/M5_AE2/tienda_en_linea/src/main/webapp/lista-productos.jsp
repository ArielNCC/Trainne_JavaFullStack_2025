<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // Obtener la lista de productos de la sesión
    java.util.List<java.util.Map<String, String>> productos = 
        (java.util.List<java.util.Map<String, String>>) session.getAttribute("productos");
    
    // Si no hay productos en la sesión, crear la lista con productos de ejemplo
    if (productos == null) {
        productos = new java.util.ArrayList<java.util.Map<String, String>>();
        
        // Crear algunos productos de ejemplo para demostración (solo si la lista no existe)
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
        
        // Guardar la lista en la sesión
        session.setAttribute("productos", productos);
    }
    
    // Calcular estadísticas
    request.setAttribute("productos", productos);
    int totalProductos = productos.size();
    int productosEnOferta = 0;
    int productosSinOferta = 0;
    
    for (java.util.Map<String, String> producto : productos) {
        if ("true".equals(producto.get("enOferta"))) {
            productosEnOferta++;
        } else {
            productosSinOferta++;
        }
    }
    
    request.setAttribute("totalProductos", totalProductos);
    request.setAttribute("productosEnOferta", productosEnOferta);
    request.setAttribute("productosSinOferta", productosSinOferta);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Productos - Tienda en Línea</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
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
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        .stat-number {
            font-size: 2em;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .stat-label {
            font-size: 0.9em;
            opacity: 0.9;
        }
        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .product-card {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            border: 1px solid #e9ecef;
            transition: transform 0.2s, box-shadow 0.2s;
            position: relative;
        }
        .product-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .product-name {
            font-size: 1.2em;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .product-info {
            margin-bottom: 8px;
            display: flex;
            justify-content: space-between;
        }
        .product-label {
            color: #666;
            font-weight: 500;
        }
        .product-value {
            color: #333;
        }
        .product-price {
            font-size: 1.3em;
            font-weight: bold;
            color: #28a745;
        }
        .oferta-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: #dc3545;
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
            animation: pulse 2s infinite;
        }
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }
        .categoria-electronica {
            border-left: 4px solid #007bff;
        }
        .categoria-ropa {
            border-left: 4px solid #e83e8c;
        }
        .categoria-hogar {
            border-left: 4px solid #28a745;
        }
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        .empty-state img {
            width: 100px;
            height: 100px;
            margin-bottom: 20px;
            opacity: 0.5;
        }
        .navigation {
            text-align: center;
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 12px 25px;
            margin: 5px 10px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            font-weight: 500;
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
        .clear-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }
        .clear-btn:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📋 Lista de Productos Registrados</h1>
        
        <!-- Estadísticas -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number">${totalProductos}</div>
                <div class="stat-label">Total de Productos</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${productosEnOferta}</div>
                <div class="stat-label">Productos en Oferta</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${productosSinOferta}</div>
                <div class="stat-label">Productos sin Oferta</div>
            </div>
        </div>
        
        <!-- Lista de productos -->
        <c:choose>
            <c:when test="${fn:length(productos) > 0}">
                <h3>🛍️ Productos Disponibles (${fn:length(productos)} productos encontrados)</h3>
                
                <div class="products-grid">
                    <c:forEach var="producto" items="${productos}" varStatus="status">
                        <div class="product-card categoria-${producto.categoria}">
                            <c:if test="${producto.enOferta == 'true'}">
                                <div class="oferta-badge">🔥 OFERTA</div>
                            </c:if>
                            
                            <div class="product-name">
                                <c:out value="${producto.nombre}" />
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Categoría:</span>
                                <span class="product-value">
                                    <c:choose>
                                        <c:when test="${producto.categoria == 'electronica'}">
                                            🔌 Electrónica
                                        </c:when>
                                        <c:when test="${producto.categoria == 'ropa'}">
                                            👕 Ropa y Accesorios
                                        </c:when>
                                        <c:when test="${producto.categoria == 'hogar'}">
                                            🏠 Hogar y Jardín
                                        </c:when>
                                        <c:otherwise>
                                            📦 General
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Caracteres:</span>
                                <span class="product-value">${fn:length(producto.nombre)} letras</span>
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Estado:</span>
                                <span class="product-value">
                                    <c:if test="${producto.enOferta == 'true'}">
                                        ⭐ En Oferta
                                    </c:if>
                                    <c:if test="${producto.enOferta != 'true'}">
                                        ✅ Precio Regular
                                    </c:if>
                                </span>
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Precio:</span>
                                <span class="product-price">
                                    $<fmt:formatNumber value="${producto.precio}" pattern="#,##0" /> CLP
                                </span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <!-- Botones de control -->
                <div style="text-align: center;">
                    <form action="limpiar-productos.jsp" method="post" style="display: inline; margin-right: 10px;">
                        <button type="submit" class="clear-btn" 
                                onclick="return confirm('¿Está seguro de que desea eliminar todos los productos?')">
                            🗑️ Limpiar Lista de Productos
                        </button>
                    </form>
                    
                    <form action="lista-productos.jsp" method="get" style="display: inline;">
                        <button type="submit" class="clear-btn" style="background-color: #17a2b8;">
                            🔄 Recargar Lista
                        </button>
                    </form>
                </div>
                
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div style="font-size: 4em; margin-bottom: 20px;">📦</div>
                    <h3>No hay productos registrados</h3>
                    <p>Comience registrando su primer producto para verlo aparecer aquí.</p>
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="navigation">
            <a href="registro.jsp" class="btn btn-primary">📝 Registrar Nuevo Producto</a>
            <a href="index.jsp" class="btn btn-secondary">🏠 Volver al Inicio</a>
        </div>
    </div>
</body>
</html>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Productos - Tienda en Línea</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
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
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        .stat-number {
            font-size: 2em;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .stat-label {
            font-size: 0.9em;
            opacity: 0.9;
        }
        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .product-card {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            border: 1px solid #e9ecef;
            transition: transform 0.2s, box-shadow 0.2s;
            position: relative;
        }
        .product-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .product-name {
            font-size: 1.2em;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .product-info {
            margin-bottom: 8px;
            display: flex;
            justify-content: space-between;
        }
        .product-label {
            color: #666;
            font-weight: 500;
        }
        .product-value {
            color: #333;
        }
        .product-price {
            font-size: 1.3em;
            font-weight: bold;
            color: #28a745;
        }
        .oferta-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: #dc3545;
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
            animation: pulse 2s infinite;
        }
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }
        .categoria-electronica {
            border-left: 4px solid #007bff;
        }
        .categoria-ropa {
            border-left: 4px solid #e83e8c;
        }
        .categoria-hogar {
            border-left: 4px solid #28a745;
        }
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        .empty-state img {
            width: 100px;
            height: 100px;
            margin-bottom: 20px;
            opacity: 0.5;
        }
        .navigation {
            text-align: center;
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 12px 25px;
            margin: 5px 10px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            font-weight: 500;
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
        .clear-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }
        .clear-btn:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📋 Lista de Productos Registrados</h1>
        
            <c:when test="${fn:length(productos) > 0}">
                <h3>🛍️ Productos Disponibles (${fn:length(productos)} productos encontrados)</h3>
                
                <div class="products-grid">
        
        <!-- Lista de productos -->
        <c:choose>
            <c:when test="${fn:length(productos) > 0}">
                <h3>🛍️ Productos Disponibles (${fn:length(productos)} productos encontrados)</h3>
                
                <!-- Mensaje informativo sobre productos de ejemplo -->
                <div style="background-color: #e3f2fd; padding: 15px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #2196f3;">
                    <p style="margin: 0; color: #1976d2;">
                        <strong>ℹ️ Información:</strong> 
                        <c:choose>
                            <c:when test="${fn:length(productos) == 3}">
                                Se están mostrando productos de ejemplo para demostración. Registra nuevos productos para verlos en esta lista.
                            </c:when>
                            <c:otherwise>
                                Esta lista incluye tanto productos registrados por ti como productos de ejemplo.
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
                
                <div class="products-grid">
                    <c:forEach var="producto" items="${productos}" varStatus="status">
                        <div class="product-card categoria-${producto.categoria}">
                            <c:if test="${producto.enOferta == 'true'}">
                                <div class="oferta-badge">🔥 OFERTA</div>
                            </c:if>
                            
                            <div class="product-name">
                                <c:out value="${producto.nombre}" />
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Categoría:</span>
                                <span class="product-value">
                                    <c:choose>
                                        <c:when test="${producto.categoria == 'electronica'}">
                                            🔌 Electrónica
                                        </c:when>
                                        <c:when test="${producto.categoria == 'ropa'}">
                                            👕 Ropa y Accesorios
                                        </c:when>
                                        <c:when test="${producto.categoria == 'hogar'}">
                                            🏠 Hogar y Jardín
                                        </c:when>
                                        <c:otherwise>
                                            📦 General
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Caracteres:</span>
                                <span class="product-value">${fn:length(producto.nombre)} letras</span>
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Estado:</span>
                                <span class="product-value">
                                    <c:if test="${producto.enOferta == 'true'}">
                                        ⭐ En Oferta
                                    </c:if>
                                    <c:if test="${producto.enOferta != 'true'}">
                                        ✅ Precio Regular
                                    </c:if>
                                </span>
                            </div>
                            
                            <div class="product-info">
                                <span class="product-label">Precio:</span>
                                <span class="product-price">
                                    $<fmt:formatNumber value="${producto.precio}" pattern="#,##0" /> CLP
                                </span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <!-- Botones de control -->
                <div style="text-align: center;">
                    <form action="limpiar-productos.jsp" method="post" style="display: inline; margin-right: 10px;">
                        <button type="submit" class="clear-btn" 
                                onclick="return confirm('¿Está seguro de que desea eliminar todos los productos?')">
                            🗑️ Limpiar Lista de Productos
                        </button>
                    </form>
                    
                    <form action="lista-productos.jsp" method="get" style="display: inline;">
                        <button type="submit" class="clear-btn" style="background-color: #17a2b8;">
                            🔄 Recargar Lista
                        </button>
                    </form>
                </div>
                
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div style="font-size: 4em; margin-bottom: 20px;">📦</div>
                    <h3>No hay productos registrados</h3>
                    <p>Comience registrando su primer producto para verlo aparecer aquí.</p>
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="navigation">
            <a href="registro.jsp" class="btn btn-primary">📝 Registrar Nuevo Producto</a>
            <a href="index.jsp" class="btn btn-secondary">🏠 Volver al Inicio</a>
        </div>
    </div>
</body>
</html>