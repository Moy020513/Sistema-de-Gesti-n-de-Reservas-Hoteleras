package com.damoi.habitaciones.mapper;

import com.damoi.commons.dto.habitaciones.HabitacionRequest;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.mapper.CommonMapper;
import com.damoi.habitaciones.entity.Habitacion;
import org.springframework.stereotype.Component;


@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {


    @Override
    public Habitacion requestAEntidad(HabitacionRequest request) {

        if(request == null) return null;
        return Habitacion.builder()
                .numero(request.numero())
                .tipo(request.tipo())
                .precio(request.precio())
                .capacidad(request.capacidad())
                .estadoInicial(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    @Override
    public HabitacionResponse entidadResponse(Habitacion entidad) {
        if(entidad == null) return null;

        return new HabitacionResponse(
                entidad.getId(),
                entidad.getNumero(),
                entidad.getTipo(),
                entidad.getPrecio(),
                entidad.getCapacidad()
        );


    }
}
