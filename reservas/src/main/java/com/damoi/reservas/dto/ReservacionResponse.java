package com.damoi.reservas.dto;

import com.damoi.commons.dto.habitaciones.DatosHabitacion;
import com.damoi.commons.dto.huespedes.DatosHuesped;
import com.damoi.commons.enums.EstadoReserva;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReservacionResponse(

        Long id,
        DatosHuesped huesped,
        DatosHabitacion habitacion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaHora,
        String estadoReserva
) {
}
