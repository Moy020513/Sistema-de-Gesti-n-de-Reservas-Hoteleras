package com.damoi.reservas.entities;


import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.enums.EstadoReserva;
import com.damoi.commons.utils.StringCustomUtils;
import com.damoi.commons.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDateTime fechaSalida;

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
            LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {

        validarFecha(fechaEntrada);
        validarFecha(fechaSalida);
    }

    public static Reservacion crear(
            Long idHuesped, Long idHabitacion,
            LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        validarDatos(fechaEntrada, fechaSalida);

        return Reservacion.builder()
                .idHuesped(idHuesped)
                .idHabitacion(idHabitacion)
                .fechaEntrada(fechaEntrada)
                .fechaSalida(fechaSalida)
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    public void actualizar(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        validarDatos( fechaEntrada, fechaSalida);

        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }
    public void actualizarPostCheckIn(LocalDateTime fechaSalida) {

        this.fechaSalida = fechaSalida;
    }
    private void validarNoEliminado(){
        if (this.getEstadoRegistro() == EstadoRegistro.ELIMINADO)
            throw new IllegalArgumentException("Reservacion ya fue eliminada");
    }

    public void eliminar(){
        validarNoEliminado();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    public void checkInCheckOut(EstadoReserva estadoReserva){
        this.estadoReserva = estadoReserva;
    }
}
