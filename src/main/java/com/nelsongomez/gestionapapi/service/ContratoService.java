package com.nelsongomez.gestionapapi.service;

import com.nelsongomez.gestionapapi.exception.ResourceNotFoundException;
import com.nelsongomez.gestionapapi.model.Contrato;
import com.nelsongomez.gestionapapi.model.Habitacion;
import com.nelsongomez.gestionapapi.model.Inquilino;
import com.nelsongomez.gestionapapi.repository.ContratoRepository;
import com.nelsongomez.gestionapapi.repository.HabitacionRepository;
import com.nelsongomez.gestionapapi.repository.InquilinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Contiene la logica de negocio que vincula Habitacion + Inquilino a traves
 * de un Contrato, incluyendo el cambio automatico de estado de la habitacion.
 */
@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final HabitacionRepository habitacionRepository;
    private final InquilinoRepository inquilinoRepository;

    public ContratoService(ContratoRepository contratoRepository,
                            HabitacionRepository habitacionRepository,
                            InquilinoRepository inquilinoRepository) {
        this.contratoRepository = contratoRepository;
        this.habitacionRepository = habitacionRepository;
        this.inquilinoRepository = inquilinoRepository;
    }

    @Transactional(readOnly = true)
    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Contrato buscarPorId(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Contrato> buscarPorHabitacion(Long habitacionId) {
        return contratoRepository.findByHabitacionId(habitacionId);
    }

    @Transactional(readOnly = true)
    public List<Contrato> buscarPorInquilino(Long inquilinoId) {
        return contratoRepository.findByInquilinoId(inquilinoId);
    }

    @Transactional(readOnly = true)
    public List<Contrato> buscarContratosActivos() {
        return contratoRepository.findByFechaFinIsNull();
    }

    @Transactional
    public Contrato crear(Long habitacionId, Long inquilinoId, Contrato datos) {
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Habitacion no encontrada con id: " + habitacionId));
        Inquilino inquilino = inquilinoRepository.findById(inquilinoId)
                .orElseThrow(() -> new ResourceNotFoundException("Inquilino no encontrado con id: " + inquilinoId));

        if (habitacion.getEstado() != Habitacion.EstadoHabitacion.DISPONIBLE) {
            throw new IllegalArgumentException("La habitacion " + habitacion.getCodigo() + " no esta disponible");
        }

        Contrato contrato = new Contrato(datos.getFechaInicio(), datos.getFechaFin(), habitacion, inquilino);
        habitacion.setEstado(Habitacion.EstadoHabitacion.OCUPADA);
        habitacionRepository.save(habitacion);

        return contratoRepository.save(contrato);
    }

    @Transactional
    public Contrato finalizar(Long id, java.time.LocalDate fechaFin) {
        Contrato contrato = buscarPorId(id);
        contrato.setFechaFin(fechaFin);
        contratoRepository.save(contrato);

        Habitacion habitacion = contrato.getHabitacion();
        habitacion.setEstado(Habitacion.EstadoHabitacion.DISPONIBLE);
        habitacionRepository.save(habitacion);

        return contrato;
    }

    @Transactional
    public void eliminar(Long id) {
        Contrato existente = buscarPorId(id);
        contratoRepository.delete(existente);
    }
}
