# 📘 Base de Datos chile_fdc

Base de datos relacional diseñada como proyecto personal para almacenar y consultar información nutricional proveniente de la API FoodData Central (FDC).
Incluye tablas normalizadas, relaciones N:M, vistas optimizadas y ejemplos de consultas útiles.

## 📌 Contenido

- [Descripción del Proyecto](#-descripción-del-proyecto)
- [Estructura de Tablas](#-estructura-de-tablas)
- [Inserción de Datos](#-inserción-de-datos)
- [Consultas SQL Comunes](#-consultas-sql-comunes)
- [Uso de las Vistas](#-uso-de-las-vistas)
- [Diagrama ER](#-diagrama-er)
- [Notas Técnicas](#-notas-técnicas)
- [Docker Compose](#-docker-compose---configuración)

---

## 🥑 Descripción del Proyecto

El objetivo de esta base de datos es almacenar información nutricional de alimentos, incluyendo:

- **Alimentos**
- **Nutrientes**
- **Valores nutricionales asociados**

La arquitectura utiliza un esquema clásico de relación N:M para mantener los valores de nutrientes por alimento.

---

## 🗂 Estructura de Tablas

### Tabla `alimento`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id_alimento` | BIGINT (PK) | ID original (fdcId) |
| `descripcion` | VARCHAR | Nombre del alimento |
| `brand_owner` | VARCHAR | Marca o fabricante |

### Tabla `nutriente`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id_nutriente` | BIGINT (PK) | ID del nutriente |
| `nombre` | VARCHAR | Nombre del nutriente |
| `unidad_medida` | VARCHAR | Unidad (g, mg, IU, etc.) |

### Tabla puente `alimento_nutriente`

**Relación N:M entre alimento y nutriente.**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id_alimento` | FK → alimento | |
| `id_nutriente` | FK → nutriente | |
| `valor` | DOUBLE | Cantidad del nutriente |

---

## 🥼 Inserción de Datos

Ejemplo:

```sql
INSERT INTO alimento (id_alimento, descripcion, brand_owner)
VALUES (1750340, 'Apples, fuji, with skin, raw', NULL);

INSERT INTO nutriente (id_nutriente, nombre, unidad_medida)
VALUES (1051, 'Water', 'g');

INSERT INTO alimento_nutriente (id_alimento, id_nutriente, valor)
VALUES (1750340, 1051, 83.61);
```

---

## 🔍 Consultas SQL Comunes

### 1️⃣ Obtener todos los nutrientes de un alimento

```sql
SELECT a.descripcion, n.nombre, an.valor, n.unidad_medida
FROM alimento a
JOIN alimento_nutriente an ON an.id_alimento = a.id_alimento
JOIN nutriente n ON n.id_nutriente = an.id_nutriente
WHERE a.id_alimento = 1750340;
```

### 2️⃣ Buscar alimentos por palabra clave

```sql
SELECT *
FROM alimento
WHERE descripcion LIKE '%apple%';
```

### 3️⃣ Contar cuántos nutrientes tiene cada alimento

```sql
SELECT a.descripcion, COUNT(*) AS cantidad_nutrientes
FROM alimento a
JOIN alimento_nutriente an ON an.id_alimento = a.id_alimento
GROUP BY a.descripcion;
```

---

## 🧭 Uso de las Vistas

Las vistas permiten consultar datos rápidamente sin escribir JOINs complejos.

### 🪟 Vista 1: `vista_contenido_nutricional_por_id`

Muestra todos los nutrientes de cada alimento.

**✔ Uso:**
```sql
SELECT *
FROM vista_contenido_nutricional_por_id
WHERE id_alimento = 1750340;
```

### 🪟 Vista 2: `vista_nutrientes_clave_por_descripcion`

Resume los nutrientes clave (agua, proteínas, calorías, grasa total).

**✔ Buscar alimentos con sus nutrientes importantes:**
```sql
SELECT *
FROM vista_nutrientes_clave_por_descripcion
WHERE descripcion LIKE '%apple%';
```

---

## 🧩 Diagrama ER (ASCII)

```
        +-------------+               +--------------+
        |  ALIMENTO   |               |  NUTRIENTE   |
        +-------------+               +--------------+
        | id_alimento |<---\     /--->| id_nutriente |
        | descripcion |     \   /     | nombre       |
        | brand_owner |      \ /      | unidad_medida|
        +-------------+       X       +--------------+
                             / \
                            /   \
                           /     \
                          /  N:M  \
                         /         \
                (ALIMENTO_NUTRIENTE)
             +----------------------------+
             | id_alimento (FK)           |
             | id_nutriente (FK)          |
             | valor                      |
             +----------------------------+
```

---

## ⚙️ Notas Técnicas

- **Motor:** MySQL / MariaDB
- **Codificación:** utf8mb4
- **Normalización:** Cumple 1FN, 2FN y 3FN
- La tabla puente evita duplicaciones y mantiene integridad referencial
- Las vistas permiten consultas rápidas desde aplicaciones externas

---

## 🐳 Docker Compose - Configuración

Este proyecto usa Docker Compose para correr MySQL y phpMyAdmin.

### 🚀 Inicio Rápido

#### 1. Iniciar los contenedores
```bash
docker-compose up -d
```

#### 2. Acceder a phpMyAdmin
Abre tu navegador en: http://localhost:8081

**Credenciales:**
- **Usuario:** `root`
- **Contraseña:** `system`

#### 3. Conectar desde tu aplicación Java
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/chile_fdc
spring.datasource.username=fdc-admin
spring.datasource.password=fdc1234
```

### 🛠️ Comandos Útiles

#### Ver logs de los contenedores
```bash
docker-compose logs -f
```

#### Detener los contenedores
```bash
docker-compose down
```

#### Detener y eliminar volúmenes (limpia la base de datos)
```bash
docker-compose down -v
```

#### Reiniciar los contenedores
```bash
docker-compose restart
```

### 📊 Información de Conexión

| Servicio | Host | Puerto | Usuario | Contraseña |
|----------|------|--------|---------|------------|
| MySQL | localhost | 3307 | root | system |
| MySQL | localhost | 3307 | fdc-admin | fdc1234 |
| phpMyAdmin | localhost | 8081 | root | system |

### 📁 Estructura

```
FDC-BBDD/
├── docker-compose.yml       # Configuración de Docker
├── fdc-api-bbdd.sql         # Script SQL (se ejecuta automáticamente)
└── README-fdc-BBDD.md       # Esta guía
```

### ⚙️ Configuración Docker

#### Puertos
- **MySQL:** 3307 (puerto tradicional 3306 + 1)
- **phpMyAdmin:** 8081 (puerto tradicional 80 + 1)

#### Base de datos
- **Nombre:** chile_fdc
- **Charset:** utf8mb4
- **Collation:** utf8mb4_unicode_ci

#### Usuarios
- **root:** Acceso total (contraseña: system)
- **fdc-admin:** Acceso a base de datos chile_fdc (contraseña: fdc1234)

### 🔄 Actualizar el script SQL

Si modificas `fdc-api-bbdd.sql`, necesitas recrear los contenedores:

```bash
docker-compose down -v
docker-compose up -d
```

### 🐛 Troubleshooting

#### Puerto en uso
Si el puerto 3307 u 8081 ya está en uso, edita `docker-compose.yml` y cambia el puerto:
```yaml
ports:
  - "3308:3306"  # Cambiar 3307 a 3308
```

#### Ver si los contenedores están corriendo
```bash
docker ps
```

#### Acceder al contenedor MySQL
```bash
docker exec -it fdc-mysql mysql -u root -psystem
```
