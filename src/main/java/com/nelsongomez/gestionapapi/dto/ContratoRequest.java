package com.nelsongomez.gestionapapi.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO de entrada para crear un Contrato: solo pide los IDs de habitacion e
 * inquilino en lugar de exigir los objetos completos en el JSON.
 */
public class ContratoRequest {

    @NotNull(message = "El id de la habitacion es obligatorio")
    private Long habitacionId;

    @NotNull(message = "El id del inquilino es obligatorio")
    private Long inquilinoId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    public Long getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(Long habitacionId) {
        this.habitacionId = habitacionId;
    }

    public Long getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(Long inquilinoId) {
        this.inquilinoId = inquilinoId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
