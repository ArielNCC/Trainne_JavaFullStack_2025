package com.skillnest.miniagenda;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.skillnest.miniagenda.model.Evento;
import com.skillnest.miniagenda.service.AgendaService;

/**
 * Clase de pruebas para la aplicación Mini Agenda
 * 
 * @SpringBootTest: Indica que esta clase contiene pruebas de integración
 *                  Carga el contexto completo de Spring para las pruebas
 */
@SpringBootTest
class MiniagendaApplicationTests {

	/**
	 * @Autowired inyecta el servicio real de la aplicación
	 * Permite probar la funcionalidad con el contexto de Spring cargado
	 */
	@Autowired
	private AgendaService agendaService;
	
	private Evento eventoTest;
	
	/**
	 * @BeforeEach se ejecuta antes de cada prueba
	 * Inicializa datos comunes para las pruebas
	 */
	@BeforeEach
	void setUp() {
		// Crear un evento de prueba
		eventoTest = new Evento(
			null,
			"Evento de Prueba",
			LocalDate.of(2025, 12, 31),
			"Este es un evento creado para realizar pruebas unitarias",
			"Tester Automatizado"
		);
	}
	
	/**
	 * Prueba básica: verifica que el contexto de Spring se carga correctamente
	 */
	@Test
	void contextLoads() {
		// Si el contexto se carga sin errores, esta prueba pasa
		assertNotNull(agendaService, "El servicio de agenda debe estar inyectado");
	}
	
	/**
	 * PRUEBA REQUERIDA 1: Validar el agregado de eventos
	 * Verifica que se puede agregar un evento correctamente
	 */
	@Test
	void testAgregarEvento() {
		// Arrange (Preparar)
		int cantidadInicial = agendaService.obtenerTodosLosEventos().size();
		
		// Act (Actuar)
		Evento eventoAgregado = agendaService.agregarEvento(eventoTest);
		
		// Assert (Afirmar)
		assertNotNull(eventoAgregado, "El evento agregado no debe ser null");
		assertNotNull(eventoAgregado.getId(), "El evento debe tener un ID asignado");
		assertEquals("Evento de Prueba", eventoAgregado.getTitulo(), "El título debe coincidir");
		assertEquals(LocalDate.of(2025, 12, 31), eventoAgregado.getFecha(), "La fecha debe coincidir");
		assertEquals("Este es un evento creado para realizar pruebas unitarias", 
				eventoAgregado.getDescripcion(), "La descripción debe coincidir");
		assertEquals("Tester Automatizado", eventoAgregado.getResponsable(), "El responsable debe coincidir");
		
		// Verificar que la cantidad de eventos aumentó
		int cantidadFinal = agendaService.obtenerTodosLosEventos().size();
		assertEquals(cantidadInicial + 1, cantidadFinal, "Debe haber un evento más");
		
		System.out.println("✅ Prueba de agregado de evento exitosa");
	}
	
	/**
	 * PRUEBA REQUERIDA 2: Validar la recuperación de eventos por fecha
	 * Verifica que se pueden obtener eventos de una fecha específica
	 */
	@Test
	void testObtenerEventosPorFecha() {
		// Arrange (Preparar)
		LocalDate fechaBuscada = LocalDate.of(2025, 12, 31);
		
		// Agregar el evento de prueba
		agendaService.agregarEvento(eventoTest);
		
		// Act (Actuar)
		List<Evento> eventosEncontrados = agendaService.obtenerEventosPorFecha(fechaBuscada);
		
		// Assert (Afirmar)
		assertNotNull(eventosEncontrados, "La lista de eventos no debe ser null");
		assertFalse(eventosEncontrados.isEmpty(), "Debe encontrar al menos un evento");
		
		// Verificar que todos los eventos encontrados tienen la fecha correcta
		for (Evento evento : eventosEncontrados) {
			assertEquals(fechaBuscada, evento.getFecha(), 
					"Todos los eventos deben tener la fecha buscada");
		}
		
		System.out.println("✅ Prueba de recuperación de eventos por fecha exitosa");
		System.out.println("   Se encontraron " + eventosEncontrados.size() + " eventos para la fecha " + fechaBuscada);
	}
	
	/**
	 * Prueba adicional: Verificar que se obtienen todos los eventos
	 */
	@Test
	void testObtenerTodosLosEventos() {
		// Act
		List<Evento> eventos = agendaService.obtenerTodosLosEventos();
		
		// Assert
		assertNotNull(eventos, "La lista de eventos no debe ser null");
		assertFalse(eventos.isEmpty(), "Debe haber eventos iniciales");
		
		System.out.println("✅ Prueba de obtener todos los eventos exitosa");
		System.out.println("   Total de eventos: " + eventos.size());
	}
	
	/**
	 * Prueba adicional: Verificar la búsqueda de eventos por ID
	 */
	@Test
	void testBuscarEventoPorId() {
		// Arrange
		Evento eventoAgregado = agendaService.agregarEvento(eventoTest);
		Long id = eventoAgregado.getId();
		
		// Act
		Optional<Evento> eventoEncontrado = agendaService.buscarEventoPorId(id);
		
		// Assert
		assertTrue(eventoEncontrado.isPresent(), "El evento debe ser encontrado");
		assertEquals(id, eventoEncontrado.get().getId(), "El ID debe coincidir");
		assertEquals("Evento de Prueba", eventoEncontrado.get().getTitulo(), "El título debe coincidir");
		
		System.out.println("✅ Prueba de búsqueda por ID exitosa");
	}
	
	/**
	 * Prueba adicional: Verificar la modificación de eventos
	 */
	@Test
	void testModificarEvento() {
		// Arrange
		Evento eventoAgregado = agendaService.agregarEvento(eventoTest);
		
		// Modificar el evento
		eventoAgregado.setTitulo("Evento Modificado");
		eventoAgregado.setDescripcion("Descripción actualizada para prueba");
		
		// Act
		boolean modificado = agendaService.modificarEvento(eventoAgregado);
		
		// Assert
		assertTrue(modificado, "El evento debe ser modificado exitosamente");
		
		Optional<Evento> eventoModificado = agendaService.buscarEventoPorId(eventoAgregado.getId());
		assertTrue(eventoModificado.isPresent(), "El evento modificado debe existir");
		assertEquals("Evento Modificado", eventoModificado.get().getTitulo(), "El título debe estar actualizado");
		assertEquals("Descripción actualizada para prueba", 
				eventoModificado.get().getDescripcion(), "La descripción debe estar actualizada");
		
		System.out.println("✅ Prueba de modificación de evento exitosa");
	}
	
	/**
	 * Prueba adicional: Verificar la eliminación de eventos
	 */
	@Test
	void testEliminarEvento() {
		// Arrange
		Evento eventoAgregado = agendaService.agregarEvento(eventoTest);
		Long id = eventoAgregado.getId();
		
		// Act
		boolean eliminado = agendaService.eliminarEvento(id);
		
		// Assert
		assertTrue(eliminado, "El evento debe ser eliminado exitosamente");
		
		Optional<Evento> eventoBuscado = agendaService.buscarEventoPorId(id);
		assertFalse(eventoBuscado.isPresent(), "El evento eliminado no debe existir");
		
		System.out.println("✅ Prueba de eliminación de evento exitosa");
	}
	
	/**
	 * Prueba adicional: Verificar que no se puede buscar un evento inexistente
	 */
	@Test
	void testBuscarEventoInexistente() {
		// Act
		Optional<Evento> eventoInexistente = agendaService.buscarEventoPorId(99999L);
		
		// Assert
		assertFalse(eventoInexistente.isPresent(), "No debe encontrar un evento con ID inexistente");
		
		System.out.println("✅ Prueba de búsqueda de evento inexistente exitosa");
	}
}
