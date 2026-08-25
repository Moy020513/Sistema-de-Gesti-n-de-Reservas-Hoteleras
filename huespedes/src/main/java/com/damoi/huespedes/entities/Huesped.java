package com.damoi.huespedes.entities;

import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="HUESPEDES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED")
    private Long id;
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;
    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;
    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;
    @Column(name = "TELEFONO", length = 10, nullable = false)
    private String telefono;
    @Column(name = "DOCUMENTO", length = 20, nullable = false)
    private String documento;
    @Column(name = "NACIONALIDAD", length = 20, nullable = false)
    private String nacionalidad;
    @Column(name = "ESTADO_REGISTRO", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;

    private void validarNoEliminado(){
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalStateException("El medico ya esta eliminado");
    }

    public void validarDatos(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono,
                   String documento, String nacionalidad) {

        StringCustomUtils.validarTamanio(nombre, 2, 50,
                "Nombre no puede ser nulo y debe contener 2-50 caracteres" );
        StringCustomUtils.validarTamanio(apellidoPaterno, 2, 50,
                "Nombre no puede ser nulo y debe contener 2-50 caracteres" );
        StringCustomUtils.validarTamanio(apellidoMaterno, 2, 50,
                "Nombre no puede ser nulo y debe contener 2-50 caracteres" );
        StringCustomUtils.validarTamanio(email, 6, 100,
                "Nombre no puede ser nulo y debe contener 6-100 caracteres" );
        StringCustomUtils.validarTamanio(telefono, 10, 10,
                "Nombre no puede ser nulo y debe contener 10 caracteres" );
        StringCustomUtils.validarTamanio(documento, 6, 20,
                "Nombre no puede ser nulo y debe contener 2-50 caracteres" );
        StringCustomUtils.validarTamanio(nacionalidad, 2, 20,
                "Nombre no puede ser nulo y debe contener 2-50 caracteres" );
    }

    public void eliminar(){
        validarNoEliminado();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    public void actualizarHuesped(String nombre, String apellidoPaterno, String apellidoMaterno, String email,
                   String telefono, String documento, String nacionalidad) {
        validarNoEliminado();
        validarDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono, documento, nacionalidad);

        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.documento = documento;
        this.nacionalidad = nacionalidad;
    }
}
