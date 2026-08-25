package com.damoi.habitaciones.entity;


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
    private EstadoInicial estadoInicial ;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    public void validarDatos(Double numero, String Tipo, BigDecimal precio,
                             Integer capacidad) {
        if (this.numero == null || this.numero <= 0) {
            throw new IllegalArgumentException("El número de habitación debe ser mayor a 0");
        }
        if (this.tipo == null || this.tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de habitación es obligatorio");
        }
        if (this.precio == null || this.precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (this.capacidad == null || this.capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        if (this.estadoInicial == null) {
            throw new IllegalArgumentException("El estado inicial es obligatorio");
        }
        if (this.estadoRegistro == null) {
            throw new IllegalArgumentException("El estado de registro es obligatorio");
        }
    }

}
