# Colección de Pruebas - Sistema Catálogo

Esta es una colección de requests tipo Postman/curl para verificar el funcionamiento de todos los endpoints del Sistema Catálogo. El sistema incluye tanto interfaz web JSP como API REST.

## 1. Autenticación

### Login como Administrador
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "nombreUsuario": "admin",
    "contrasena": "admin123"
  }'
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "nombreUsuario": "admin",
  "email": "admin@correo.com",
  "rol": "ADMIN"
}
```

### Login como Usuario Normal
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "nombreUsuario": "usuario1",
    "contrasena": "usuario123"
  }'
```

## 2. Endpoints de Productos (Lectura - Sin Autenticación)

### Obtener todos los productos (con paginación)
```bash
curl -X GET "http://localhost:8081/api/v1/productos?page=0&size=10&sort=nombre&direction=asc" \
  -H "Accept: application/json"
```

### Obtener todos los productos (sin paginación)
```bash
curl -X GET http://localhost:8081/api/v1/productos/all \
  -H "Accept: application/json"
```

### Obtener producto por ID
```bash
curl -X GET http://localhost:8081/api/v1/productos/1 \
  -H "Accept: application/json"
```

### Buscar productos por nombre
```bash
curl -X GET "http://localhost:8081/api/v1/productos/buscar?nombre=Teclado" \
  -H "Accept: application/json"
```

### Obtener productos con stock disponible
```bash
curl -X GET http://localhost:8081/api/v1/productos/con-stock \
  -H "Accept: application/json"
```

## 3. Endpoints de Productos (Escritura - Requiere Autenticación ADMIN)

**Nota:** Reemplaza `[TOKEN_ADMIN]` con el token obtenido del login como admin.

### Crear nuevo producto
```bash
curl -X POST http://localhost:8081/api/v1/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_ADMIN]" \
  -d '{
    "nombre": "Smartphone Galaxy",
    "descripcion": "Smartphone Android de última generación",
    "precio": 599999,
    "stockDisponible": 15
  }'
```

### Actualizar producto completo
```bash
curl -X PUT http://localhost:8081/api/v1/productos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_ADMIN]" \
  -d '{
    "nombre": "Teclado Mecánico Actualizado",
    "descripcion": "Teclado mecánico RGB con switches Blue",
    "precio": 54990,
    "stockDisponible": 30
  }'
```

### Actualizar solo el stock
```bash
curl -X PATCH "http://localhost:8081/api/v1/productos/1/stock?stock=50" \
  -H "Authorization: Bearer [TOKEN_ADMIN]"
```

### Eliminar producto
```bash
curl -X DELETE http://localhost:8081/api/v1/productos/2 \
  -H "Authorization: Bearer [TOKEN_ADMIN]"
```

## 4. Pruebas de Error

### Crear producto sin autenticación (debería fallar)
```bash
curl -X POST http://localhost:8081/api/v1/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Producto Test",
    "descripcion": "Test sin auth",
    "precio": 10000,
    "stockDisponible": 5
  }'
```

### Crear producto con token de usuario normal (debería fallar)
**Nota:** Reemplaza `[TOKEN_USER]` con el token obtenido del login como usuario1.

```bash
curl -X POST http://localhost:8081/api/v1/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_USER]" \
  -d '{
    "nombre": "Producto Test",
    "descripcion": "Test con user",
    "precio": 10000,
    "stockDisponible": 5
  }'
```

### Obtener producto inexistente
```bash
curl -X GET http://localhost:8081/api/v1/productos/99999 \
  -H "Accept: application/json"
```

### Crear producto con datos inválidos
```bash
curl -X POST http://localhost:8081/api/v1/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_ADMIN]" \
  -d '{
    "nombre": "",
    "descripcion": "Producto con nombre vacío",
    "precio": -100,
    "stockDisponible": -5
  }'
```

## 5. Documentación OpenAPI

### Acceder a especificación OpenAPI
Obten la documentación de la API en:
```
http://localhost:8081/v3/api-docs
```

### Obtener especificación OpenAPI (JSON)
```bash
curl -X GET http://localhost:8081/v3/api-docs \
  -H "Accept: application/json"
```

## 6. Interfaz Web JSP

### Acceso a la interfaz web
Abre en el navegador:
```
http://localhost:8081
```

### Páginas disponibles
- **Login**: http://localhost:8081/login
- **Panel Principal**: http://localhost:8081/panel
- **Productos**: http://localhost:8081/productos
- **API Tester**: http://localhost:8081/api-tester (Mini-Postman JSP)
- **Admin Panel**: http://localhost:8081/admin/panel (solo ADMIN)

## 7. Pruebas de CORS

### Verificar headers CORS
```bash
curl -X OPTIONS http://localhost:8081/api/v1/productos \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type, Authorization" \
  -v
```

## 7. Respuestas Esperadas por Código de Estado

### 200 OK
- GET requests exitosos
- PUT requests exitosos
- PATCH requests exitosos

### 201 Created
- POST requests exitosos para crear productos

### 400 Bad Request
- Datos de producto inválidos
- Parámetros incorrectos

### 401 Unauthorized
- Sin token JWT
- Token JWT inválido o expirado

### 403 Forbidden
- Usuario normal intentando operaciones de admin

### 404 Not Found
- Producto no existe
- Endpoint inexistente

### 500 Internal Server Error
- Errores del servidor

## 8. Estructura de Respuesta Estándar

### Respuesta Exitosa (Producto)
```json
{
  "id": 1,
  "nombre": "Teclado mecánico",
  "descripcion": "Teclado mecánico retroiluminado RGB",
  "precio": 49990,
  "stockDisponible": 25
}
```

### Respuesta de Error
```json
{
  "error": "Producto no encontrado",
  "id": 99999
}
```

### Respuesta de Paginación
```json
{
  "productos": [...],
  "currentPage": 0,
  "totalItems": 50,
  "totalPages": 5,
  "size": 10
}
```

## 9. Automatización con Script

Para automatizar estas pruebas, puedes usar este script bash:

```bash
#!/bin/bash

BASE_URL="http://localhost:8081"

echo "=== Probando API Catálogo de Productos ==="

# 1. Login como admin
echo "1. Login como admin..."
ADMIN_TOKEN=$(curl -s -X POST $BASE_URL/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"nombreUsuario":"admin","contrasena":"admin123"}' | \
  grep -o '"token":"[^"]*"' | cut -d'"' -f4)

echo "Token admin obtenido: ${ADMIN_TOKEN:0:20}..."

# 2. Obtener productos
echo "2. Obteniendo productos..."
curl -s -X GET $BASE_URL/api/v1/productos/all | jq '.[0:2]'

# 3. Crear producto
echo "3. Creando producto..."
curl -s -X POST $BASE_URL/api/v1/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{
    "nombre": "Producto Test Script",
    "descripcion": "Creado por script",
    "precio": 19990,
    "stockDisponible": 10
  }' | jq '.'

echo "=== Pruebas completadas ==="
```