<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>API Tester - Mini Postman</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .json-viewer {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            padding: 15px;
            font-family: 'Courier New', monospace;
            font-size: 14px;
            white-space: pre-wrap;
            word-wrap: break-word;
            max-height: 400px;
            overflow-y: auto;
        }
        .success-response { border-left: 4px solid #28a745; }
        .error-response { border-left: 4px solid #dc3545; }
        .method-badge { font-weight: bold; padding: 5px 10px; border-radius: 3px; }
        .method-get { background: #17a2b8; color: white; }
        .method-put { background: #ffc107; color: black; }
        .method-delete { background: #dc3545; color: white; }
        .endpoint-card { margin-bottom: 20px; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/panel">Sistema</a>
            <div class="navbar-nav ms-auto">
                <sec:authentication property="name" var="username"/>
                <span class="navbar-text me-3">${username} (<sec:authentication property="authorities"/>)</span>
                <a href="/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h2>🔧 API Tester - Mini Postman</h2>
                <p class="text-muted">
                    Sesión activa como: <strong><sec:authentication property="name"/></strong> | 
                    Rol: <strong><sec:authentication property="authorities"/></strong>
                </p>
                <div class="alert alert-info">
                    <strong>🔑 Token JWT:</strong>
                    <input type="text" class="form-control mt-2" value="${token}" readonly onclick="this.select()" style="font-family: monospace; font-size: 12px;">
                    <small class="text-muted">Copia este token para usar en Postman: <code>Authorization: Bearer [token]</code></small>
                </div>
                <hr>
            </div>
        </div>

        <!-- GET All Products -->
        <div class="card endpoint-card">
            <div class="card-header bg-info text-white">
                <span class="method-badge method-get">GET</span> /api/productos/listar
            </div>
            <div class="card-body">
                <p>Obtener lista de todos los productos</p>
                <button class="btn btn-info" onclick="getAllProducts()">Ejecutar GET</button>
                <div id="response-get-all" class="json-viewer mt-3" style="display:none;"></div>
            </div>
        </div>

        <!-- GET Product by ID -->
        <div class="card endpoint-card">
            <div class="card-header bg-info text-white">
                <span class="method-badge method-get">GET</span> /api/productos/{id}
            </div>
            <div class="card-body">
                <p>Obtener un producto por ID</p>
                <div class="row">
                    <div class="col-md-3">
                        <input type="number" class="form-control" id="get-id" placeholder="ID del producto" min="1">
                    </div>
                    <div class="col-md-9">
                        <button class="btn btn-info" onclick="getProductById()">Ejecutar GET</button>
                    </div>
                </div>
                <div id="response-get-id" class="json-viewer mt-3" style="display:none;"></div>
            </div>
        </div>

        <!-- POST Create Product -->
        <div class="card endpoint-card">
            <div class="card-header bg-success text-white">
                <span class="method-badge method-put" style="background: #28a745;">POST</span> /api/productos
            </div>
            <div class="card-body">
                <p>Crear un nuevo producto (Solo ADMIN)</p>
                <div class="row">
                    <div class="col-md-4">
                        <input type="text" class="form-control" id="post-nombre" placeholder="Nombre">
                    </div>
                    <div class="col-md-3">
                        <input type="number" class="form-control" id="post-precio" placeholder="Precio" step="0.01">
                    </div>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="post-descripcion" placeholder="Descripción">
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-success" onclick="createProduct()">Ejecutar POST</button>
                    </div>
                </div>
                <div id="response-post" class="json-viewer mt-3" style="display:none;"></div>
            </div>
        </div>

        <!-- PUT Update Product -->
        <div class="card endpoint-card">
            <div class="card-header bg-warning text-dark">
                <span class="method-badge method-put">PUT</span> /api/productos/{id}
            </div>
            <div class="card-body">
                <p>Actualizar un producto (Solo ADMIN)</p>
                <div class="row">
                    <div class="col-md-2">
                        <input type="number" class="form-control" id="put-id" placeholder="ID" min="1">
                    </div>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="put-nombre" placeholder="Nombre">
                    </div>
                    <div class="col-md-2">
                        <input type="number" class="form-control" id="put-precio" placeholder="Precio" step="0.01">
                    </div>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="put-descripcion" placeholder="Descripción">
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-warning" onclick="updateProduct()">Ejecutar PUT</button>
                    </div>
                </div>
                <div id="response-put" class="json-viewer mt-3" style="display:none;"></div>
            </div>
        </div>

        <!-- DELETE Product -->
        <div class="card endpoint-card">
            <div class="card-header bg-danger text-white">
                <span class="method-badge method-delete">DELETE</span> /api/productos/{id}
            </div>
            <div class="card-body">
                <p>Eliminar un producto (Solo ADMIN)</p>
                <div class="row">
                    <div class="col-md-3">
                        <input type="number" class="form-control" id="delete-id" placeholder="ID del producto" min="1">
                    </div>
                    <div class="col-md-9">
                        <button class="btn btn-danger" onclick="deleteProduct()">Ejecutar DELETE</button>
                    </div>
                </div>
                <div id="response-delete" class="json-viewer mt-3" style="display:none;"></div>
            </div>
        </div>

        <div class="alert alert-info">
            <strong>ℹ️ Nota:</strong> Las operaciones PUT y DELETE están restringidas a usuarios con rol ADMIN. Si intentas ejecutarlas con rol USER, recibirás un error 403 Forbidden en formato JSON.
        </div>
    </div>

    <script>
        // Función para mostrar respuesta
        function displayResponse(elementId, data, status) {
            const element = document.getElementById(elementId);
            element.style.display = 'block';
            element.className = 'json-viewer mt-3 ' + (status >= 200 && status < 300 ? 'success-response' : 'error-response');
            element.textContent = JSON.stringify(data, null, 2);
        }

        // GET All Products
        async function getAllProducts() {
            try {
                const response = await fetch('/api/productos/listar', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include' // Incluye cookies de sesión
                });
                const data = await response.json();
                displayResponse('response-get-all', data, response.status);
            } catch (error) {
                displayResponse('response-get-all', { error: error.message }, 500);
            }
        }

        // GET Product by ID
        async function getProductById() {
            const id = document.getElementById('get-id').value.trim();
            if (!id || isNaN(id) || id <= 0) {
                alert('Por favor ingresa un ID válido (número mayor a 0)');
                return;
            }
            try {
                const response = await fetch('/api/productos/' + id, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include'
                });
                
                if (!response.ok) {
                    const errorData = await response.json().catch(() => ({ 
                        error: 'Producto no encontrado',
                        status: response.status 
                    }));
                    displayResponse('response-get-id', errorData, response.status);
                    return;
                }
                
                const data = await response.json();
                displayResponse('response-get-id', data, response.status);
            } catch (error) {
                displayResponse('response-get-id', { error: error.message }, 500);
            }
        }

        // POST Create Product
        async function createProduct() {
            const nombre = document.getElementById('post-nombre').value.trim();
            const precio = document.getElementById('post-precio').value.trim();
            const descripcion = document.getElementById('post-descripcion').value.trim();

            if (!nombre || !precio || isNaN(precio)) {
                alert('Por favor completa Nombre y Precio (número) correctamente');
                return;
            }

            try {
                const response = await fetch('/api/productos', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        nombre: nombre,
                        precio: parseFloat(precio),
                        descripcion: descripcion
                    })
                });

                if (response.status === 403) {
                    displayResponse('response-post', {
                        error: 'Acceso denegado',
                        mensaje: 'Solo usuarios con rol ADMIN pueden crear productos',
                        status: 403
                    }, response.status);
                    return;
                }

                const data = await response.json();
                displayResponse('response-post', data, response.status);
                
                // Limpiar formulario si fue exitoso
                if (response.status === 201) {
                    document.getElementById('post-nombre').value = '';
                    document.getElementById('post-precio').value = '';
                    document.getElementById('post-descripcion').value = '';
                }
            } catch (error) {
                displayResponse('response-post', { error: error.message }, 500);
            }
        }

        // PUT Update Product
        async function updateProduct() {
            const id = document.getElementById('put-id').value.trim();
            const nombre = document.getElementById('put-nombre').value.trim();
            const precio = document.getElementById('put-precio').value.trim();
            const descripcion = document.getElementById('put-descripcion').value.trim();

            if (!id || !nombre || !precio || isNaN(id) || isNaN(precio)) {
                alert('Por favor completa ID (número), Nombre y Precio (número) correctamente');
                return;
            }

            try {
                const response = await fetch('/api/productos/' + id, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        nombre: nombre,
                        precio: parseFloat(precio),
                        descripcion: descripcion
                    })
                });

                if (response.status === 403) {
                    displayResponse('response-put', {
                        error: 'Acceso denegado',
                        mensaje: 'Solo usuarios con rol ADMIN pueden actualizar productos',
                        status: 403
                    }, response.status);
                    return;
                }

                const data = await response.json();
                displayResponse('response-put', data, response.status);
            } catch (error) {
                displayResponse('response-put', { error: error.message }, 500);
            }
        }

        // DELETE Product
        async function deleteProduct() {
            const id = document.getElementById('delete-id').value.trim();
            if (!id || isNaN(id) || id <= 0) {
                alert('Por favor ingresa un ID válido (número mayor a 0)');
                return;
            }

            if (!confirm('¿Estás seguro de eliminar este producto?')) {
                return;
            }

            try {
                const response = await fetch('/api/productos/' + id, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include'
                });

                if (response.status === 403) {
                    displayResponse('response-delete', {
                        error: 'Acceso denegado',
                        mensaje: 'Solo usuarios con rol ADMIN pueden eliminar productos',
                        status: 403
                    }, response.status);
                    return;
                }

                const data = await response.json();
                displayResponse('response-delete', data, response.status);
            } catch (error) {
                displayResponse('response-delete', { error: error.message }, 500);
            }
        }
    </script>
</body>
</html>
