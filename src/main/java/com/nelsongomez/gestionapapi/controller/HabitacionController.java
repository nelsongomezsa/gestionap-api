package com.nelsongomez.gestionapapi.controller;

import com.nelsongomez.gestionapapi.model.Habitacion;
import com.nelsongomez.gestionapapi.service.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public List<Habitacion> listar(@RequestParam(required = false) Habitacion.EstadoHabitacion estado) {
        if (estado != null) {
            return habitacionService.buscarPorEstado(estado);
        }
        return habitacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Habitacion buscarPorId(@PathVariable Long id) {
        return habitacionService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Habitacion crear(@Valid @RequestBody Habitacion habitacion) {
        return habitacionService.crear(habitacion);
    }

    @PutMapping("/{id}")
    public Habitacion actualizar(@PathVariable Long id, @Valid @RequestBody Habitacion habitacion) {
        return habitacionService.actualizar(id, habitacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
