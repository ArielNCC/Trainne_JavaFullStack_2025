package com.skillnest.miniagenda.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.skillnest.miniagenda.model.Evento;

/**
 * Interfaz que define los servicios de la agenda
 * Define el contrato que debe cumplir la implementación del servicio
 */
public interface AgendaService {
    
    /**
     * Agrega un nuevo evento a la agenda
     * @param evento El evento a agregar
     * @return El evento agregado con su ID asignado
     */
    Evento agregarEvento(Evento evento);
    
    /**
     * Obtiene todos los eventos de la agenda
     * @return Lista con todos los eventos
     */
    List<Evento> obtenerTodosLosEventos();
    
    /**
     * Obtiene eventos agrupados por fecha
     * @return Map donde la clave es la fecha y el valor es la lista de eventos de ese día
     */
    Map<LocalDate, List<Evento>> obtenerEventosAgrupadosPorFecha();
    
    /**
     * Busca un evento por su ID
     * @param id El ID del evento a buscar
     * @return Optional con el evento si existe, empty si no
     */
    Optional<Evento> buscarEventoPorId(Long id);
    
    /**
     * Obtiene los eventos de una fecha específica
     * @param fecha La fecha a buscar
     * @return Lista de eventos en esa fecha
     */
    List<Evento> obtenerEventosPorFecha(LocalDate fecha);
    
    /**
     * Modifica un evento existente
     * @param evento El evento con los datos actualizados
     * @return true si se modificó correctamente, false si no existe
     */
    boolean modificarEvento(Evento evento);
    
    /**
     * Elimina un evento por su ID
     * @param id El ID del evento a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    boolean eliminarEvento(Long id);
}
