package com.nelsongomez.gestionapapi.repository;

import com.nelsongomez.gestionapapi.model.Inquilino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {

    Optional<Inquilino> findByDocumentoIdentidad(String documentoIdentidad);

    Optional<Inquilino> findByEmail(String email);
}
