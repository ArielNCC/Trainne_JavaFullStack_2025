package com.empresa.capacitaciones.rest;

import com.empresa.capacitaciones.entity.Curso;
import com.empresa.capacitaciones.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoRestController {
    
    @Autowired
    private CursoService cursoService;
    
    /**
     * GET /api/cursos - Obtener todos los cursos
     */
    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        List<Curso> cursos = cursoService.findAll();
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * GET /api/cursos/disponibles - Obtener cursos disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<Curso>> listarCursosDisponibles() {
        List<Curso> cursos = cursoService.findCursosDisponibles();
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * GET /api/cursos/con-cupos - Obtener cursos con cupos disponibles
     */
    @GetMapping("/con-cupos")
    public ResponseEntity<List<Curso>> listarCursosConCupos() {
        List<Curso> cursos = cursoService.findCursosConCuposDisponibles();
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * GET /api/cursos/{id} - Obtener un curso por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCursoPorId(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.findById(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Curso no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * POST /api/cursos - Crear un nuevo curso (solo ADMIN)
     */
    @PostMapping
    public ResponseEntity<?> crearCurso(@RequestBody Curso curso) {
        try {
            Curso nuevoCurso = cursoService.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCurso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(crearRespuestaError("Error al crear el curso: " + e.getMessage()));
        }
    }
    
    /**
     * PUT /api/cursos/{id} - Actualizar un curso (solo ADMIN)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            if (!cursoService.findById(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError("Curso no encontrado"));
            }
            curso.setId(id);
            Curso cursoActualizado = cursoService.save(curso);
            return ResponseEntity.ok(cursoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(crearRespuestaError("Error al actualizar el curso: " + e.getMessage()));
        }
    }
    
    /**
     * DELETE /api/cursos/{id} - Eliminar un curso (solo ADMIN)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
        try {
            if (!cursoService.findById(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError("Curso no encontrado"));
            }
            cursoService.deleteById(id);
            return ResponseEntity.ok(crearRespuestaExito("Curso eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(crearRespuestaError("Error al eliminar el curso: " + e.getMessage()));
        }
    }
    
    /**
     * GET /api/cursos/buscar?nombre={nombre} - Buscar cursos por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Curso>> buscarCursosPorNombre(@RequestParam String nombre) {
        List<Curso> cursos = cursoService.findByNombre(nombre);
        return ResponseEntity.ok(cursos);
    }
    
    // Métodos auxiliares para respuestas
    private Map<String, Object> crearRespuestaError(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("mensaje", mensaje);
        return response;
    }
    
    private Map<String, Object> crearRespuestaExito(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("mensaje", mensaje);
        return response;
    }
}
