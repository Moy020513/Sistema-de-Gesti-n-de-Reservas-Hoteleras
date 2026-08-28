package com.damoi.huespedes.repository;

import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.huespedes.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> getByEstadoRegistroAndId(EstadoRegistro estadoRegistro, Long id);
    List<Huesped> getByEstadoRegistro(EstadoRegistro estadoRegistro);
    boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
    boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
    boolean existsByDocumentoIgnoreCaseAndEstadoRegistro(String documento, EstadoRegistro estadoRegistro);
    boolean existsByTelefonoAndEstadoRegistroAndIdNot(String telefono, EstadoRegistro estadoRegistro, Long id);
    boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(String email, EstadoRegistro estadoRegistro, Long id);
    boolean existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(String documento,
                                                                 EstadoRegistro estadoRegistro, Long id);

}
