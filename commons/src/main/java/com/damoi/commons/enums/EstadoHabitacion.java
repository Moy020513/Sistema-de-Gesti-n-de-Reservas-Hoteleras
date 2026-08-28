package com.damoi.commons.enums;

import com.damoi.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoHabitacion {
    DISPONIBLE(1L, "Habitacion Disponible" ),
    OCUPADA(2L, "Habitacion Ocupada"),
    LIMPÍEZA(3L, "Habitacion en Limpieza"),
    MANTENIMIENTO(4L, "Habitacion en Mantenimiento");


    private final Long codigo;
    private final String descripcion;

    public static EstadoHabitacion obtenerEstadoHabitacionPorCodigo(Long codigo){
        if (codigo == null || codigo < 0)
            throw new IllegalArgumentException("Codigo debe ser positivo o 0");

        for (EstadoHabitacion reserva:values()){
            if (Objects.equals(reserva.codigo, codigo))
                return reserva;
        }
        throw new RecursoNoEncontradoException("No existe estado de Venta con el codigo "+ codigo);
    }
}
