package com.nelsongomez.gestionapapi.controller;

import com.nelsongomez.gestionapapi.dto.ContratoRequest;
import com.nelsongomez.gestionapapi.model.Contrato;
import com.nelsongomez.gestionapapi.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public List<Contrato> listar(@RequestParam(required = false) Boolean activos) {
        if (Boolean.TRUE.equals(activos)) {
            return contratoService.buscarContratosActivos();
        }
        return contratoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Contrato buscarPorId(@PathVariable Long id) {
        return contratoService.buscarPorId(id);
    }

    @GetMapping("/habitacion/{habitacionId}")
    public List<Contrato> buscarPorHabitacion(@PathVariable Long habitacionId) {
        return contratoService.buscarPorHabitacion(habitacionId);
    }

    @GetMapping("/inquilino/{inquilinoId}")
    public List<Contrato> buscarPorInquilino(@PathVariable Long inquilinoId) {
        return contratoService.buscarPorInquilino(inquilinoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrato crear(@Valid @RequestBody ContratoRequest request) {
        Contrato datos = new Contrato();
        datos.setFechaInicio(request.getFechaInicio());
        datos.setFechaFin(request.getFechaFin());
        return contratoService.crear(request.getHabitacionId(), request.getInquilinoId(), datos);
    }

    @PatchMapping("/{id}/finalizar")
    public Contrato finalizar(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        LocalDate fechaFin = (body != null && body.get("fechaFin") != null)
                ? LocalDate.parse(body.get("fechaFin"))
                : LocalDate.now();
        return contratoService.finalizar(id, fechaFin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
