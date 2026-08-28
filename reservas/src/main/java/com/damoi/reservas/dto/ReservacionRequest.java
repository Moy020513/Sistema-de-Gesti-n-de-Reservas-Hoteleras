package com.damoi.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ReservacionRequest(

        @NotNull(message = "El id del huesped es requerido")
        @Positive(message = "El id del huesped debe ser positivo")
        Long idHuesped,

        @NotNull(message = "El id de la habitación es requerido")
        @Positive(message = "El id del médico debe ser positivo")
        Long idHabitacion,

        @NotNull(message = "La fecha de ingreso es requerida")
        @FutureOrPresent(message = "La fecha de la cita debe ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaHora,

        @NotNull(message = "La fecha de salida es requerida")
        @FutureOrPresent(message = "La fecha de la cita debe ser futura y posterior a la fecha de ingreso")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                LocalDateTime fechaSalida
) {
}
