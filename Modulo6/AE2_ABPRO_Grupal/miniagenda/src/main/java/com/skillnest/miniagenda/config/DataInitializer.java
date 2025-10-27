package com.skillnest.miniagenda.config;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.skillnest.miniagenda.model.Evento;
import com.skillnest.miniagenda.repository.EventoRepository;

/**
 * Componente que inicializa datos de ejemplo en la base de datos
 * 
 * @Component: Marca esta clase como un Bean de Spring
 * CommandLineRunner: Se ejecuta automáticamente al iniciar la aplicación
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar datos si la base de datos está vacía
        if (eventoRepository.count() == 0) {
            logger.info("Inicializando datos de ejemplo en la base de datos...");
            
            // Crear eventos de ejemplo
            Evento evento1 = new Evento(null, "Reunión de Equipo", 
                    LocalDate.of(2025, 10, 25), 
                    "Reunión semanal del equipo de desarrollo para revisar avances", 
                    "Juan Pérez");
            
            Evento evento2 = new Evento(null, "Cumpleaños de María", 
                    LocalDate.of(2025, 11, 5), 
                    "Celebración del cumpleaños de María en la oficina", 
                    "Ana García");
            
            Evento evento3 = new Evento(null, "Convivencia Anual", 
                    LocalDate.of(2025, 12, 15), 
                    "Convivencia de fin de año con todos los empleados", 
                    "Recursos Humanos");
            
            // Guardar en la base de datos
            eventoRepository.save(evento1);
            eventoRepository.save(evento2);
            eventoRepository.save(evento3);
            
            logger.info("Datos de ejemplo inicializados correctamente. Total eventos: {}", eventoRepository.count());
        } else {
            logger.info("La base de datos ya contiene {} eventos. No se inicializan datos de ejemplo.", eventoRepository.count());
        }
    }
}