package com.damoi.reservas.services;


import com.damoi.commons.client.HabitacionClient;
import com.damoi.commons.client.HuespedClient;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.exceptions.RecursoNoEncontradoException;
import com.damoi.reservas.dto.ReservacionRequest;
import com.damoi.reservas.dto.ReservacionResponse;
import com.damoi.reservas.entities.Reservacion;
import com.damoi.reservas.mapper.ReservacionMapper;
import com.damoi.reservas.repository.ReservacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservacionServiceImpl implements ReservacionService{

    private final ReservacionRepository reservacionRepository;
    private final ReservacionMapper reservacionMapper;
    private final HuespedClient huespedClient;
    private final HabitacionClient habitacionClient;



    @Override
    @Transactional(readOnly = true)
    public List<ReservacionResponse> listar() {
        log.info("Listando todas las reservaciones activas");
        return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map( reservacion -> reservacionMapper.entidadResponse(
                        reservacion,
                        obtenerHuespedSinEstado(reservacion.getIdHuesped()),
                        obtenerHabitacionSinEstado(reservacion.getIdHabitacion())
                )).toList();
    }

    @Override
    public ReservacionResponse obtenerPorId(Long id) {
        Reservacion reservacion = obtenerReservacionOException(id);
        return reservacionMapper.entidadResponse(
                reservacion,
                obtenerHuespedSinEstado(reservacion.getIdHuesped()),
                obtenerHabitacionSinEstado(reservacion.getIdHabitacion())
        );
    }

    @Override
    public ReservacionResponse registrar(ReservacionRequest request) {
        return null;
    }

    @Override
    public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public void actualizarEstadoReservacion(Long idReservacion, Long idEstadoReservacion) {

    }

    @Override
    public Boolean ReservacionesActivasHuesped(Long idHuesped) {
        return null;
    }

    @Override
    public Boolean ReservacionesActivasHabitacion(Long idHabitacion) {
        return null;
    }

    private Reservacion obtenerReservacionOException(Long id) {
        log.info("Buscando reservación con id: {}", id);

        return reservacionRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Reservación no encontrada con id: " + id));
    }

    private HabitacionResponse obtenerPacienteActivo(Long id){
        log.info("Buscando habitación activa con id: {} en el servicio remoto...", id);
        return habitacionClient.obtenerHabitacionActivaPorId(id);
    }
    private HabitacionResponse obtenerHabitacionSinEstado(Long id){
        log.info("Buscando habitación sin estado con id: {} en el servicio remoto...", id);
        return habitacionClient.obtenerHabitacionPorIdSinEstado(id);
    }

    private HuespedResponse obtenerHuespedActivo(Long id){
        log.info("Buscando huesped activo con id: {} en el servicio remoto...", id);
        return huespedClient.obtenerHuespedActivoPorId(id);
    }
    private HuespedResponse obtenerHuespedSinEstado(Long id){
        log.info("Buscando huesped sin estado con id: {} en el servicio remoto...", id);
        return huespedClient.obtenerHuespedSinEstadoPorId(id);
    }
}
