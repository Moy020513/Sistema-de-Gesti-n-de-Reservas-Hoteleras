package com.damoi.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoHabitacion {
    DISPONIBLE(1L, "Habitacion Disponible" ),
    OCUPADA(2L, "Habitacion Ocupada"),
    LIMPÍEZA(3L, "Habitacion en Limpieza"),
    MANTENIMIENTO(4L, "Habitacion en Mantenimiento");


    private final Long codigo;
    private final String descripcion;
}
