package com.damoi.auth.repository;

import com.damoi.auth.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damoi.auth.entities.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEstadoRegistroAndUsername(EstadoRegistro estadoRegistro, String username);

    //boolean existsByUsername(String username);

    boolean existsByEstadoRegistroAndUsername(EstadoRegistro estadoRegistro, String username);

    void deleteByUsername(String username);
}

