package com.skillnest.agendacontactos.service;

import com.skillnest.agendacontactos.model.Contacto;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de contactos
 * 
 * Define el contrato de la capa de servicio, especificando las operaciones
 * de negocio disponibles para la gestión de contactos.
 * 
 * Beneficios de usar interfaces:
 * - Facilita las pruebas unitarias (mocking)
 * - Permite múltiples implementaciones
 * - Reduce el acoplamiento entre capas
 * - Mejora la mantenibilidad del código
 */
public interface ContactoService {
    
    /**
     * Obtiene todos los contactos registrados
     * @return Lista de todos los contactos
     */
    List<Contacto> obtenerTodosLosContactos();
    
    /**
     * Busca un contacto por su ID
     * @param id ID del contacto a buscar
     * @return Optional conteniendo el contacto si existe
     */
    Optional<Contacto> obtenerContactoPorId(Long id);
    
    /**
     * Crea un nuevo contacto
     * @param contacto Datos del contacto a crear
     * @return El contacto creado con su ID asignado
     */
    Contacto crearContacto(Contacto contacto);
    
    /**
     * Actualiza un contacto existente
     * @param id ID del contacto a actualizar
     * @param contactoActualizado Nuevos datos del contacto
     * @return Optional conteniendo el contacto actualizado
     */
    Optional<Contacto> actualizarContacto(Long id, Contacto contactoActualizado);
    
    /**
     * Elimina un contacto por su ID
     * @param id ID del contacto a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    boolean eliminarContacto(Long id);
    
    /**
     * Busca contactos por nombre (búsqueda parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de contactos que coinciden con la búsqueda
     */
    List<Contacto> buscarContactosPorNombre(String nombre);
    
    /**
     * Valida si un email ya existe en el sistema
     * @param email Email a validar
     * @param excludeId ID a excluir de la validación (útil para actualizaciones)
     * @return true si el email ya existe
     */
    boolean existeEmail(String email, Long excludeId);
}