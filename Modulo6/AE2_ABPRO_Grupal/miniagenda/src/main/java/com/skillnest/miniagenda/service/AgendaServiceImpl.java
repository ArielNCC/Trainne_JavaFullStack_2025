package com.skillnest.miniagenda.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillnest.miniagenda.model.Evento;
import com.skillnest.miniagenda.repository.EventoRepository;

/**
 * Implementación del servicio de agenda usando JPA/Hibernate
 * 
 * @Service: Marca esta clase como un Bean de tipo servicio en Spring.
 */
@Service
public class AgendaServiceImpl implements AgendaService {

    private static final Logger logger = LoggerFactory.getLogger(AgendaServiceImpl.class);
    
    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public Evento agregarEvento(Evento evento) {
        logger.debug("Agregando nuevo evento: {}", evento);
        
        Evento eventoGuardado = eventoRepository.save(evento);
        logger.info("Evento agregado exitosamente con ID: {}", eventoGuardado.getId());
        
        return eventoGuardado;
    }

    @Override
    public List<Evento> obtenerTodosLosEventos() {
        logger.debug("Obteniendo todos los eventos desde la base de datos");
        
        List<Evento> eventos = eventoRepository.findAllByOrderByFechaAsc();
        logger.info("Se obtuvieron {} eventos desde la base de datos", eventos.size());
        
        return eventos;
    }

    @Override
    public Map<LocalDate, List<Evento>> obtenerEventosAgrupadosPorFecha() {
        logger.debug("Agrupando eventos por fecha");
        
        List<Evento> todosLosEventos = eventoRepository.findAllByOrderByFechaAsc();
        Map<LocalDate, List<Evento>> eventosAgrupados = todosLosEventos.stream()
                .collect(Collectors.groupingBy(Evento::getFecha));
        
        logger.info("Eventos agrupados en {} fechas diferentes", eventosAgrupados.size());
        
        return eventosAgrupados;
    }

    @Override
    public Optional<Evento> buscarEventoPorId(Long id) {
        logger.debug("Buscando evento con ID: {}", id);
        
        return eventoRepository.findById(id);
    }

    @Override
    public List<Evento> obtenerEventosPorFecha(LocalDate fecha) {
        logger.debug("Obteniendo eventos para la fecha: {}", fecha);
        
        List<Evento> eventosFecha = eventoRepository.findByFecha(fecha);
        logger.info("Se encontraron {} eventos para la fecha {}", eventosFecha.size(), fecha);
        
        return eventosFecha;
    }

    @Override
    public boolean modificarEvento(Evento evento) {
        logger.debug("Modificando evento con ID: {}", evento.getId());
        
        if (eventoRepository.existsById(evento.getId())) {
            eventoRepository.save(evento);
            logger.info("Evento con ID {} modificado exitosamente", evento.getId());
            return true;
        }
        
        logger.warn("No se encontró el evento con ID {} para modificar", evento.getId());
        return false;
    }

    @Override
    public boolean eliminarEvento(Long id) {
        logger.debug("Eliminando evento con ID: {}", id);
        
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            logger.info("Evento con ID {} eliminado exitosamente", id);
            return true;
        }
        
        logger.warn("No se encontró el evento con ID {} para eliminar", id);
        return false;
    }
}
