package com.damoi.reservas.services;


import com.damoi.commons.client.HabitacionClient;
import com.damoi.commons.client.HuespedClient;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.enums.EstadoReserva;
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
        Reservacion reservacion = reservacionMapper.requestAEntidad(request);
        HuespedResponse huesped = obtenerHuespedActivo(request.idHuesped());
        HabitacionResponse habitacion = obtenerHabitacionActiva(request.idHuesped());
        habitacionClient.validarHabitacionDisponible(habitacion.id());
        if (request.fechaHora().isAfter(request.fechaSalida()))
            throw  new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha de salida");
        reservacionRepository.save(reservacion);
        habitacionClient.actualizarDisponibilidadHabitacion(habitacion.id(), EstadoHabitacion.OCUPADA.getCodigo());
        return reservacionMapper.entidadResponse(reservacion, huesped, habitacion);
    }

    @Override
    public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
        Reservacion reservacion = obtenerReservacionOException(id);
        if (reservacion.getIdHabitacion() != request.idHabitacion() || reservacion.getIdHuesped() != request.idHuesped() )
            throw new IllegalArgumentException("No se puede ,modificar la habitacion reservada ni el huesped que reserva");
        if (reservacion.getEstadoReserva() == EstadoReserva.CONFIRMADA){
            if (request.fechaHora().isAfter(request.fechaSalida()))
                throw  new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha de salida");
            reservacion.actualizar(request.fechaHora(), request.fechaSalida());
            return reservacionMapper.entidadResponse(reservacion);
        }
        else if (reservacion.getEstadoReserva() == EstadoReserva.EN_CURSO){
            if (request.fechaHora().isAfter(request.fechaSalida()))
                throw  new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha de salida");
            reservacion.actualizarPostCheckIn(request.fechaSalida());
            return reservacionMapper.entidadResponse(reservacion);
        }
        else{
            throw new IllegalArgumentException("La reserva en cuestion no se puede modificar");
        }
    }

    @Override
    public void eliminar(Long id) {
        Reservacion reservacion = obtenerReservacionOException(id);
        if (reservacion.getEstadoReserva() != EstadoReserva.CONFIRMADA)
            throw new IllegalArgumentException("Reserva no se puede cancelar");
        reservacion.eliminar();
        habitacionClient.actualizarDisponibilidadHabitacion(reservacion.getIdHabitacion(),
                EstadoHabitacion.DISPONIBLE.getCodigo());
    }

    @Override
    public void actualizarEstadoReservacion(Long idReservacion, Long idEstadoReservacion) {
        Reservacion reservacion = obtenerReservacionOException(idReservacion);
        reservacion.checkInCheckOut(EstadoReserva.obtenerEstadoReservaPorCodigo(idEstadoReservacion));
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

    private HabitacionResponse obtenerHabitacionActiva(Long id){
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
