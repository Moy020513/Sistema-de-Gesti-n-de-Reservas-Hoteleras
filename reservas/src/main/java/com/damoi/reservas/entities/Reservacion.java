package com.damoi.reservas.entities;


import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.enums.EstadoReserva;
import com.damoi.commons.utils.StringCustomUtils;
import com.damoi.commons.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "RESERVAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Reservacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long id;
    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_INICIAL", length = 20, nullable = false)
    private EstadoReserva estadoReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", length = 10, nullable = false)
    private EstadoRegistro estadoRegistro;



    private static void validarId(Long id, String campo) {

        ValoresNumericosUtils.validarLongPositivo(id, "El id del " + campo +
                "es requerido y debe ser positivo");
    }

    private static void validarFecha(LocalDateTime fechaHora) {
        if (fechaHora == null || !fechaHora.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("La fecha de la cita es requerida y debe ser futura");
    }

    public static void validarDatos(
            Long idHuesped, Long idHabitacion,
            LocalDateTime fechaHora) {

        validarId(idHuesped, "huesped");

        validarId(idHabitacion, "habitación");

        validarFecha(fechaHora);
    }

    public static Reservacion crear(
            Long idHuesped, Long idHabitacion,
            LocalDateTime fechaHora) {
        validarDatos(idHuesped, idHabitacion, fechaHora);

        return Reservacion.builder()
                .idHuesped(idHuesped)
                .idHabitacion(idHabitacion)
                .fechaHora(fechaHora)
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

}
