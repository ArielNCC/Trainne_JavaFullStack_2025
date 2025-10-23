package com.skillnest.agendacontactos.repository;

import com.skillnest.agendacontactos.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Contacto
 * 
 * Anotaciones utilizadas:
 * - @Repository: Marca la interfaz como un repositorio de Spring, proporcionando 
 *   traducción de excepciones y detección automática de componentes
 * 
 * Extiende JpaRepository que proporciona:
 * - Métodos CRUD básicos (save, findById, findAll, delete, etc.)
 * - Paginación y ordenamiento
 * - Métodos de consulta personalizados
 */
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    
    /**
     * Busca contactos por nombre (búsqueda parcial, case-insensitive)
     * Spring Data JPA genera automáticamente la implementación
     */
    List<Contacto> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca un contacto por correo electrónico
     */
    Optional<Contacto> findByCorreo(String correo);
    
    /**
     * Verifica si existe un contacto con el correo dado, excluyendo un ID específico
     * Útil para validaciones de actualización
     */
    @Query("SELECT COUNT(c) > 0 FROM Contacto c WHERE c.correo = :correo AND c.id != :id")
    boolean existsByCorreoAndIdNot(@Param("correo") String correo, @Param("id") Long id);
    
    /**
     * Verifica si existe un contacto con el correo dado
     */
    boolean existsByCorreo(String correo);
    
    /**
     * Busca contactos por teléfono
     */
    Optional<Contacto> findByTelefono(String telefono);
    
    /**
     * Obtiene los últimos contactos ordenados por ID (más recientes primero)
     */
    List<Contacto> findTop5ByOrderByIdDesc();
}