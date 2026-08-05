package com.nelsongomez.gestionapapi.service;

import com.nelsongomez.gestionapapi.exception.ResourceNotFoundException;
import com.nelsongomez.gestionapapi.model.Habitacion;
import com.nelsongomez.gestionapapi.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @Transactional(readOnly = true)
    public List<Habitacion> listarTodas() {
        return habitacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Habitacion buscarPorId(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitacion no encontrada con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Habitacion> buscarPorEstado(Habitacion.EstadoHabitacion estado) {
        return habitacionRepository.findByEstado(estado);
    }

    @Transactional
    public Habitacion crear(Habitacion habitacion) {
        habitacionRepository.findByCodigo(habitacion.getCodigo()).ifPresent(h -> {
            throw new IllegalArgumentException("Ya existe una habitacion con el codigo: " + habitacion.getCodigo());
        });
        return habitacionRepository.save(habitacion);
    }

    @Transactional
    public Habitacion actualizar(Long id, Habitacion datos) {
        Habitacion existente = buscarPorId(id);
        existente.setCodigo(datos.getCodigo());
        existente.setDireccion(datos.getDireccion());
        existente.setPrecioMensual(datos.getPrecioMensual());
        existente.setEstado(datos.getEstado());
        return habitacionRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Habitacion existente = buscarPorId(id);
        habitacionRepository.delete(existente);
    }
}
