package com.skillnest.agendacontactos.service;

import com.skillnest.agendacontactos.model.Contacto;
import com.skillnest.agendacontactos.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de contactos utilizando JPA Repository
 * 
 * @Service: Marca la clase como un servicio de Spring
 * @Autowired: Inyecta automáticamente el repositorio
 */
@Service
public class ContactoServiceImpl implements ContactoService {
    
    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    public Contacto crearContacto(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    @Override
    public List<Contacto> obtenerTodosLosContactos() {
        return contactoRepository.findAll();
    }

    @Override
    public Optional<Contacto> obtenerContactoPorId(Long id) {
        return contactoRepository.findById(id);
    }

    @Override
    public List<Contacto> buscarContactosPorNombre(String nombre) {
        return contactoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public boolean eliminarContacto(Long id) {
        if (contactoRepository.existsById(id)) {
            contactoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Contacto> actualizarContacto(Long id, Contacto contactoActualizado) {
        return contactoRepository.findById(id)
                .map(contacto -> {
                    contacto.setNombre(contactoActualizado.getNombre());
                    contacto.setCorreo(contactoActualizado.getCorreo());
                    contacto.setTelefono(contactoActualizado.getTelefono());
                    return contactoRepository.save(contacto);
                });
    }

    @Override
    public boolean existeEmail(String email, Long excludeId) {
        if (excludeId != null) {
            return contactoRepository.existsByCorreoAndIdNot(email, excludeId);
        } else {
            return contactoRepository.existsByCorreo(email);
        }
    }
}
