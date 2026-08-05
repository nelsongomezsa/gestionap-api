package com.nelsongomez.gestionapapi.repository;

import com.nelsongomez.gestionapapi.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByHabitacionId(Long habitacionId);

    List<Contrato> findByInquilinoId(Long inquilinoId);

    List<Contrato> findByFechaFinIsNull();
}
