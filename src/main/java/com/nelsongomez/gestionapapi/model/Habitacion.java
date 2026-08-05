package com.nelsongomez.gestionapapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una habitacion disponible para alquiler.
 * Es la version "API" de la entidad equivalente en el proyecto de escritorio GestionAp.
 */
@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El identificador de la habitacion es obligatorio")
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotNull(message = "El precio mensual es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(name = "precio_mensual", nullable = false)
    private BigDecimal precioMensual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoHabitacion estado = EstadoHabitacion.DISPONIBLE;

    @JsonIgnore
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();

    public Habitacion() {
    }

    public Habitacion(String codigo, String direccion, BigDecimal precioMensual) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.precioMensual = precioMensual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public enum EstadoHabitacion {
        DISPONIBLE,
        OCUPADA,
        EN_MANTENIMIENTO
    }
}
