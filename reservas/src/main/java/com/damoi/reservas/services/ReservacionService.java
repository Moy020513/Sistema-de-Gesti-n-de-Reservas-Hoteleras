package com.damoi.reservas.services;

import com.damoi.commons.service.CrudService;
import com.damoi.reservas.dto.ReservacionRequest;
import com.damoi.reservas.dto.ReservacionResponse;

public interface ReservacionService extends CrudService<ReservacionRequest, ReservacionResponse> {


    void actualizarEstadoReservacion(Long idReservacion, Long idEstadoReservacion);

    Boolean ReservacionesActivasHuesped(Long idHuesped);

    Boolean ReservacionesActivasHabitacion(Long idHabitacion);
}
