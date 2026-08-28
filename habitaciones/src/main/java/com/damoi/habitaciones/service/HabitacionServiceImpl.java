package com.damoi.habitaciones.service;


import com.damoi.commons.dto.habitaciones.HabitacionRequest;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.exceptions.RecursoNoEncontradoException;
import com.damoi.habitaciones.entity.Habitacion;
import com.damoi.habitaciones.mapper.HabitacionMapper;
import com.damoi.habitaciones.repository.HabitacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {


    private final HabitacionRepository habitacionRepository;

    private final HabitacionMapper habitacionMapper;


    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando todos los pacientes");

        return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(habitacionMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        return habitacionMapper.entidadResponse(obtenerHabitacionActiva(id));
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerHabitacionPorId(Long id) {
        return habitacionMapper.entidadResponse(
                habitacionRepository.findById(id)
                        .orElseThrow(()->
                                new RecursoNoEncontradoException(
                                        "Habitación no encontrada con id: " + id)));
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando nueva habitación...");

        validarDatosUnicos(request);
        Habitacion habitacion = habitacionMapper.requestAEntidad(
                request
        );

        habitacionRepository.save(habitacion);
        log.info("Habitación registrada correctamente");
        return habitacionMapper.entidadResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = obtenerHabitacionActiva(id);
        log.info("Actualizando habitación con id: {}", id);

        validarCambiosUnicos(request, id);

        habitacion.actualizar(
                request.numero(),
                request.tipo(),
                request.precio(),
                request.capacidad()
        );
        return habitacionMapper.entidadResponse(habitacion);
    }

    @Override
    public void eliminar(Long id) {

        Habitacion habitacion = obtenerHabitacionActiva(id);
        log.info("Eliminando habitación con id: {}", id);

        habitacion.setEstatusEliminado();
        log.info("Paciente con id {} eliminado correctamente", habitacion.getId());

    }

    @Override
    public boolean buscarHabitacionDisponible(Long id) {
        return habitacionRepository.existsByEstadoInicialAndEstadoRegistroAndId(
                EstadoHabitacion.DISPONIBLE, EstadoRegistro.ACTIVO, id);
    }

    private Habitacion obtenerHabitacionActiva(Long id) {
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Habitación activa no encontrada con id: " + id));
    }

    private void validarDatosUnicos(HabitacionRequest request) {
        log.info("Validando habitación única...");
        if (habitacionRepository.existsByNumeroIgnoreCase(request.numero())) {
            throw new IllegalArgumentException("Ya existe una habitación registrada con el número: "
                    + request.numero());
        }
    }

    private void validarCambiosUnicos(HabitacionRequest request, Long id) {
        log.info("Validando habitación única...");
        if (habitacionRepository.existsByNumeroIgnoreCaseAndIdNot(request.numero(), id)) {
            throw new IllegalArgumentException("Ya existe una habitación registrada con el número: "
                    + request.numero());
        }
    }
}
