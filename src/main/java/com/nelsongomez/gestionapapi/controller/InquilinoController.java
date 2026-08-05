package com.nelsongomez.gestionapapi.controller;

import com.nelsongomez.gestionapapi.model.Inquilino;
import com.nelsongomez.gestionapapi.service.InquilinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquilinos")
public class InquilinoController {

    private final InquilinoService inquilinoService;

    public InquilinoController(InquilinoService inquilinoService) {
        this.inquilinoService = inquilinoService;
    }

    @GetMapping
    public List<Inquilino> listar() {
        return inquilinoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Inquilino buscarPorId(@PathVariable Long id) {
        return inquilinoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inquilino crear(@Valid @RequestBody Inquilino inquilino) {
        return inquilinoService.crear(inquilino);
    }

    @PutMapping("/{id}")
    public Inquilino actualizar(@PathVariable Long id, @Valid @RequestBody Inquilino inquilino) {
        return inquilinoService.actualizar(id, inquilino);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inquilinoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
