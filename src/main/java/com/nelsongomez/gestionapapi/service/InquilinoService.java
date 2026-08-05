package com.nelsongomez.gestionapapi.service;

import com.nelsongomez.gestionapapi.exception.ResourceNotFoundException;
import com.nelsongomez.gestionapapi.model.Inquilino;
import com.nelsongomez.gestionapapi.repository.InquilinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InquilinoService {

    private final InquilinoRepository inquilinoRepository;

    public InquilinoService(InquilinoRepository inquilinoRepository) {
        this.inquilinoRepository = inquilinoRepository;
    }

    @Transactional(readOnly = true)
    public List<Inquilino> listarTodos() {
        return inquilinoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Inquilino buscarPorId(Long id) {
        return inquilinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquilino no encontrado con id: " + id));
    }

    @Transactional
    public Inquilino crear(Inquilino inquilino) {
        inquilinoRepository.findByDocumentoIdentidad(inquilino.getDocumentoIdentidad()).ifPresent(i -> {
            throw new IllegalArgumentException("Ya existe un inquilino con el documento: " + inquilino.getDocumentoIdentidad());
        });
        inquilinoRepository.findByEmail(inquilino.getEmail()).ifPresent(i -> {
            throw new IllegalArgumentException("Ya existe un inquilino con el email: " + inquilino.getEmail());
        });
        return inquilinoRepository.save(inquilino);
    }

    @Transactional
    public Inquilino actualizar(Long id, Inquilino datos) {
        Inquilino existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setDocumentoIdentidad(datos.getDocumentoIdentidad());
        existente.setEmail(datos.getEmail());
        existente.setTelefono(datos.getTelefono());
        return inquilinoRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Inquilino existente = buscarPorId(id);
        inquilinoRepository.delete(existente);
    }
}
