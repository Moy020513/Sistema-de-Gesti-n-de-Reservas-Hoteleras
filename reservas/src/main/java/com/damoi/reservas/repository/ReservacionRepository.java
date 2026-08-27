package com.damoi.reservas.repository;

import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.reservas.entities.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {


    List<Reservacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    Optional<Reservacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
}
