package com.damoi.habitaciones.repository;

import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.habitaciones.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    boolean existsByNumeroIgnoreCase(Double numero);

    boolean existsByNumeroIgnoreCaseAndIdNot(Double numero, Long id);
}
