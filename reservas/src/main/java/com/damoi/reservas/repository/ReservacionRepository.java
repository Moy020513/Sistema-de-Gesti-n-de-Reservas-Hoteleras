package com.damoi.reservas.repository;

import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.enums.EstadoReserva;
import com.damoi.reservas.entities.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {


    List<Reservacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    Optional<Reservacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
    Optional<Reservacion> findByIdHuespedAndEstadoRegistro(Long idHuesped, EstadoRegistro estadoRegistro);
    Optional<Reservacion> findByIdHabitacionAndEstadoRegistro(Long idHuesped, EstadoRegistro estadoRegistro);
}
