package com.empresa.capacitaciones.rest;

import com.empresa.capacitaciones.entity.Inscripcion;
import com.empresa.capacitaciones.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class InscripcionRestController {
    
    @Autowired
    private InscripcionService inscripcionService;
    
    /**
     * GET /api/inscripciones - Obtener todas las inscripciones
     */
    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripciones() {
        List<Inscripcion> inscripciones = inscripcionService.findAll();
        return ResponseEntity.ok(inscripciones);
    }
    
    /**
     * GET /api/inscripciones/{id} - Obtener una inscripción por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInscripcionPorId(@PathVariable Long id) {
        Optional<Inscripcion> inscripcion = inscripcionService.findById(id);
        if (inscripcion.isPresent()) {
            return ResponseEntity.ok(inscripcion.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Inscripción no encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * GET /api/inscripciones/empleado/{empleadoId} - Obtener inscripciones de un empleado
     */
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Inscripcion>> obtenerInscripcionesPorEmpleado(@PathVariable Long empleadoId) {
        List<Inscripcion> inscripciones = inscripcionService.findByEmpleadoId(empleadoId);
        return ResponseEntity.ok(inscripciones);
    }
    
    /**
     * GET /api/inscripciones/curso/{cursoId} - Obtener inscripciones de un curso
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Inscripcion>> obtenerInscripcionesPorCurso(@PathVariable Long cursoId) {
        List<Inscripcion> inscripciones = inscripcionService.findByCursoId(cursoId);
        return ResponseEntity.ok(inscripciones);
    }
    
    /**
     * POST /api/inscripciones - Registrar a un empleado en un curso
     * Body: {"empleadoId": 1, "cursoId": 2}
     */
    @PostMapping
    public ResponseEntity<?> inscribirEmpleado(@RequestBody Map<String, Long> request) {
        try {
            Long empleadoId = request.get("empleadoId");
            Long cursoId = request.get("cursoId");
            
            if (empleadoId == null || cursoId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError("Se requieren empleadoId y cursoId"));
            }
            
            Inscripcion inscripcion = inscripcionService.inscribirEmpleado(empleadoId, cursoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(inscripcion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(crearRespuestaError(e.getMessage()));
        }
    }
    
    /**
     * PUT /api/inscripciones/{id}/confirmar - Confirmar una inscripción
     */
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarInscripcion(@PathVariable Long id) {
        try {
            inscripcionService.confirmarInscripcion(id);
            return ResponseEntity.ok(crearRespuestaExito("Inscripción confirmada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(crearRespuestaError(e.getMessage()));
        }
    }
    
    /**
     * PUT /api/inscripciones/{id}/cancelar - Cancelar una inscripción
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarInscripcion(@PathVariable Long id) {
        try {
            inscripcionService.cancelarInscripcion(id);
            return ResponseEntity.ok(crearRespuestaExito("Inscripción cancelada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(crearRespuestaError(e.getMessage()));
        }
    }
    
    /**
     * PUT /api/inscripciones/{id}/completar - Completar una inscripción
     */
    @PutMapping("/{id}/completar")
    public ResponseEntity<?> completarInscripcion(@PathVariable Long id) {
        try {
            inscripcionService.completarInscripcion(id);
            return ResponseEntity.ok(crearRespuestaExito("Inscripción completada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(crearRespuestaError(e.getMessage()));
        }
    }
    
    /**
     * DELETE /api/inscripciones/{id} - Eliminar una inscripción
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInscripcion(@PathVariable Long id) {
        try {
            if (!inscripcionService.findById(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError("Inscripción no encontrada"));
            }
            inscripcionService.deleteById(id);
            return ResponseEntity.ok(crearRespuestaExito("Inscripción eliminada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(crearRespuestaError("Error al eliminar la inscripción: " + e.getMessage()));
        }
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
