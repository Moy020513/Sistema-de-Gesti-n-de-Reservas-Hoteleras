package com.damoi.habitaciones.repository;

import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.enums.EstadoReserva;
import com.damoi.habitaciones.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    boolean existsByNumeroIgnoreCase(Integer numero);

    boolean existsByNumeroIgnoreCaseAndIdNot(Integer numero, Long id);

    boolean existsByEstadoInicialAndEstadoRegistroAndId(EstadoHabitacion estado, EstadoRegistro registro, Long id);
}
