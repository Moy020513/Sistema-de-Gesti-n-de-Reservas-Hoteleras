package com.damoi.auth.entities;

import java.util.Set;

import com.damoi.auth.enums.EstadoRegistro;
import com.damoi.auth.utils.StringCustomUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USUARIOS_OAUTH")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USUARIOS_ROLES",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROL")
    )
    private Set<Rol> roles;

    @Column(name="ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    public void validarPassword(String contraseña){
        StringCustomUtils.validarTamanio(contraseña, 8, 20,
                "Password es requerido y debe contener 8-20 caracteres");
    }

    public void actualizarPassword(String password){
        this.password = password;
    }
}