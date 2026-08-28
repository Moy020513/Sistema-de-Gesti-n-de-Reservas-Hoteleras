package com.damoi.commons.enums;


import com.damoi.commons.exceptions.RecursoNoEncontradoException;
import com.damoi.commons.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoReserva {
    CONFIRMADA(1L, "Reservación confirmada por el huesped" ),
    EN_CURSO(2L, "Huesped llegó a su reservación"),
    FINALIZADA(3L, "Reservación finalizada"),
    CANCELADA(4L, "Reservación cancelda");


    private final Long codigo;
    private final String descripcion;

    public static EstadoReserva obtenerEstadoReservaPorCodigo(Long codigo){
        if (codigo == null || codigo < 0)
            throw new IllegalArgumentException("Codigo debe ser positivo o 0");

        for (EstadoReserva reserva:values()){
            if (Objects.equals(reserva.codigo, codigo))
                return reserva;
        }
        throw new RecursoNoEncontradoException("No existe estado de Venta con el codigo "+ codigo);
    }
}
