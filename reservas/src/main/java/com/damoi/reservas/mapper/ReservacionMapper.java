package com.damoi.reservas.mapper;

import com.damoi.commons.dto.habitaciones.DatosHabitacion;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.dto.huespedes.DatosHuesped;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.commons.mapper.CommonMapper;
import com.damoi.reservas.dto.ReservacionRequest;
import com.damoi.reservas.dto.ReservacionResponse;
import com.damoi.reservas.entities.Reservacion;
import org.springframework.stereotype.Component;


@Component
public class ReservacionMapper implements CommonMapper<ReservacionRequest, ReservacionResponse, Reservacion> {
    @Override
    public Reservacion requestAEntidad(ReservacionRequest request) {
        if(request == null) return null;

        return Reservacion.crear(
                request.idHuesped(),
                request.idHabitacion(),
                request.fechaHora(),
                request.fechaSalida()
        );
    }

    @Override
    public ReservacionResponse entidadResponse(Reservacion entidad) {

        if(entidad == null) return null;


        return new ReservacionResponse(
                entidad.getId(),
                null,
                null,
                entidad.getFechaEntrada(),
                entidad.getFechaSalida(),
                entidad.getEstadoReserva().getDescripcion()
        );
    }

    public ReservacionResponse entidadResponse(Reservacion entidad, HuespedResponse huesped, HabitacionResponse habitacion) {
        if(entidad == null) return null;

        return new ReservacionResponse(
                entidad.getId(),
                reservacionResponseADatosHuesped(huesped),
                habitacionResponseADatosHabitacion(habitacion),
                entidad.getFechaEntrada(),
                entidad.getFechaSalida(),
                entidad.getEstadoReserva().getDescripcion()
        );
    }

    private DatosHuesped reservacionResponseADatosHuesped(HuespedResponse huesped) {
        if (huesped == null) return null;

        return new DatosHuesped(
                huesped.nombre(),
                huesped.email(),
                huesped.telefono(),
                huesped.documento(),
                huesped.nacionalidad()
        );
    }

    private DatosHabitacion habitacionResponseADatosHabitacion(HabitacionResponse habitacion) {
        if (habitacion == null) return null;

        return new DatosHabitacion(
                habitacion.numero(),
                habitacion.tipo(),
                habitacion.precio(),
                habitacion.capacidad()

        );
    }
}
