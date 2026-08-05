package com.nelsongomez.gestionapapi.repository;

import com.nelsongomez.gestionapapi.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByCodigo(String codigo);

    List<Habitacion> findByEstado(Habitacion.EstadoHabitacion estado);
}
