package com.damoi.habitaciones.entity;


import com.damoi.commons.enums.EstadoHabitacion;
import com.damoi.commons.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "HABITACIONES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @Column(name = "NUMERO", nullable = false)
    private Double numero;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_INICIAL", nullable = false)
    private EstadoHabitacion estadoInicial ;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    public void validarDatos(Double numero, String Tipo, BigDecimal precio,
                             Integer capacidad) {
        if (numero == null || this.numero <= 0) {
            throw new IllegalArgumentException("El número de habitación debe ser mayor a 0");
        }
        if (tipo == null || this.tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de habitación es obligatorio");
        }
        if (precio == null || this.precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (capacidad == null || this.capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
    }

    public void actualizar(Double numero, String Tipo, BigDecimal precio,
                           Integer capacidad) {
        validarDatos( numero, Tipo,  precio,
                 capacidad);
        this.numero = numero;
        this.tipo = tipo.trim();
        this.precio = precio;
        this.capacidad = capacidad;
    }

    public void setEstatusEliminado() {
        estadoRegistro = EstadoRegistro.ELIMINADO;
    }

}
