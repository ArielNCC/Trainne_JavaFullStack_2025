# Documentación de @Transactional en Biblioteca SkillNest

## 📋 Resumen

La anotación `@Transactional` se aplica en los métodos de servicio que realizan operaciones de escritura (INSERT, UPDATE, DELETE) en la base de datos. Esto garantiza que todas las operaciones dentro del método se ejecuten de forma atómica: **o todas se completan exitosamente, o ninguna se aplica**.

---

## 📍 Ubicación de @Transactional

### 1. AutorService.java

**Ubicación:** `src/main/java/com/skillnest/web/services/AutorService.java`

#### Métodos con @Transactional:

```java
/**
 * Registrar un nuevo autor (JPA)
 * @Transactional asegura que la operación sea atómica
 */
@Transactional
public Autor registrarAutor(Autor autor) {
    return autorRepository.save(autor);
}
```
- **Línea:** ~52
- **Operación:** INSERT en tabla `autores`
- **Descripción:** Registra un nuevo autor en la base de datos. Si falla, no se guarda ningún dato.

---

```java
/**
 * Actualizar un autor existente (JPA)
 */
@Transactional
public Autor actualizarAutor(Autor autor) {
    if (autorRepository.existsById(autor.getId())) {
        return autorRepository.save(autor);
    }
    throw new RuntimeException("Autor no encontrado con ID: " + autor.getId());
}
```
- **Línea:** ~60
- **Operación:** UPDATE en tabla `autores`
- **Descripción:** Actualiza los datos de un autor existente. Si falla la validación o la actualización, se revierte todo.

---

```java
/**
 * Eliminar un autor (JPA)
 */
@Transactional
public void eliminarAutor(Long id) {
    if (autorRepository.existsById(id)) {
        autorRepository.deleteById(id);
    } else {
        throw new RuntimeException("Autor no encontrado con ID: " + id);
    }
}
```
- **Línea:** ~71
- **Operación:** DELETE en tabla `autores`
- **Descripción:** Elimina un autor de la base de datos. Si el autor no existe, lanza excepción y no se realizan cambios.

---

### 2. LibroService.java

**Ubicación:** `src/main/java/com/skillnest/web/services/LibroService.java`

#### Métodos con @Transactional:

```java
/**
 * Registrar un nuevo libro (JPA)
 * @Transactional asegura que la operación sea atómica
 */
@Transactional
public Libro registrarLibro(Libro libro) {
    return libroRepository.save(libro);
}
```
- **Línea:** ~66
- **Operación:** INSERT en tabla `libros`
- **Descripción:** Registra un nuevo libro asociado a un autor. Si falla, no se guarda ningún dato.

---

```java
/**
 * Actualizar un libro existente (JPA)
 */
@Transactional
public Libro actualizarLibro(Libro libro) {
    if (libroRepository.existsById(libro.getId())) {
        return libroRepository.save(libro);
    }
    throw new RuntimeException("Libro no encontrado con ID: " + libro.getId());
}
```
- **Línea:** ~74
- **Operación:** UPDATE en tabla `libros`
- **Descripción:** Actualiza los datos de un libro existente. Si falla la validación o la actualización, se revierte todo.

---

```java
/**
 * Eliminar un libro (JPA)
 */
@Transactional
public void eliminarLibro(Long id) {
    if (libroRepository.existsById(id)) {
        libroRepository.deleteById(id);
    } else {
        throw new RuntimeException("Libro no encontrado con ID: " + id);
    }
}
```
- **Línea:** ~85
- **Operación:** DELETE en tabla `libros`
- **Descripción:** Elimina un libro de la base de datos. Si el libro no existe, lanza excepción y no se realizan cambios.

---

## 🔄 ¿Cómo funciona @Transactional?

### Comportamiento Normal (sin errores):
1. Se inicia una transacción
2. Se ejecutan todas las operaciones del método
3. Si todo es exitoso, se hace **COMMIT** (se guardan todos los cambios)

### Comportamiento con Error:
1. Se inicia una transacción
2. Se ejecutan operaciones
3. Si ocurre una excepción (RuntimeException o Error):
   - Se hace **ROLLBACK** (se revierten todos los cambios)
   - La base de datos vuelve al estado anterior
   - Se propaga la excepción al controlador

---

## 📝 Ejemplo de Uso Transaccional

### Escenario: Registrar Autor y Libro

```java
// En el controlador o servicio
@Transactional
public void registrarAutorConLibro() {
    // 1. Registrar autor (transaccional)
    Autor autor = new Autor("Gabriel", "García Márquez");
    autor = autorService.registrarAutor(autor); // ✅ Guardado
    
    // 2. Registrar libro (transaccional)
    Libro libro = new Libro();
    libro.setTitulo("Cien años de soledad");
    libro.setIsbn("978-0307474728");
    libro.setAutor(autor);
    
    // Si esto falla...
    libro = libroService.registrarLibro(libro); // ❌ Error!
    
    // Resultado: AMBAS operaciones se revierten
    // - El autor NO se guarda
    // - El libro NO se guarda
    // - La base de datos queda consistente
}
```

---

## ✅ Beneficios de @Transactional

1. **Atomicidad:** Todas las operaciones se completan o ninguna se aplica
2. **Consistencia:** La base de datos siempre está en un estado válido
3. **Aislamiento:** Las transacciones no interfieren entre sí
4. **Durabilidad:** Los cambios confirmados son permanentes

---

## 🧪 Pruebas de Transaccionalidad

**Archivo de Prueba:** `src/test/java/com/skillnest/web/TransactionalOperationsTest.java`

La clase `TransactionalOperationsTest` contiene pruebas que validan:
- ✅ Registro exitoso de autor y libro
- ✅ Validaciones de campos obligatorios
- ✅ Manejo de errores con rollback automático
- ✅ Asociaciones correctas entre entidades

Para ejecutar las pruebas:
```bash
mvn test
```

---

## 📊 Resumen de Métodos Transaccionales

| Servicio | Método | Operación | Línea |
|----------|--------|-----------|-------|
| AutorService | registrarAutor() | INSERT | ~52 |
| AutorService | actualizarAutor() | UPDATE | ~60 |
| AutorService | eliminarAutor() | DELETE | ~71 |
| LibroService | registrarLibro() | INSERT | ~66 |
| LibroService | actualizarLibro() | UPDATE | ~74 |
| LibroService | eliminarLibro() | DELETE | ~85 |

**Total de métodos transaccionales:** 6

---

## 🎯 Conclusión

La aplicación implementa correctamente `@Transactional` en todos los métodos de escritura (INSERT, UPDATE, DELETE), garantizando:
- Integridad de datos
- Consistencia en operaciones complejas
- Manejo automático de rollback en caso de errores
- Cumplimiento con los requisitos del ejercicio

---

**Fecha de documentación:** 6 de noviembre de 2025  
**Autor:** Ariel
