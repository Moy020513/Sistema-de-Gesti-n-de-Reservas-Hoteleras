package com.damoi.commons.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoReserva {
    CONFIRMADA(1L, "Reservación confirmada por el huesped" ),
    EN_CURSO(2L, "Huesped llegó a su reservación"),
    FINALIZADA(3L, "Reservación finalizada"),
    CANCELADA(4L, "Reservación cancelda");


    private final Long codigo;
    private final String descripcion;
}
