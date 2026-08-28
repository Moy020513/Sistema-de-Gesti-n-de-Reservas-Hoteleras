package com.damoi.auth.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.damoi.auth.enums.EstadoRegistro;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.damoi.auth.dto.UsuarioRequest;
import com.damoi.auth.dto.UsuarioResponse;
import com.damoi.auth.entities.Rol;
import com.damoi.auth.entities.Usuario;
import com.damoi.auth.mapper.UsuarioMapper;
import com.damoi.auth.repository.RolRepository;
import com.damoi.auth.repository.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Set<UsuarioResponse> listar() {
        log.info("Listado de todos los usuarios solicitado");
        return usuarioRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(usuarioMapper::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        log.info("Buscando usuario {}", request.username());
        if (usuarioRepository.existsByEstadoRegistroAndUsername(EstadoRegistro.ACTIVO, request.username())) {
            throw new IllegalArgumentException("El usuario " + request.username() + " ya está registrado");
        }

        Set<Rol> roles = request.roles().stream().map(rol ->
                rolRepository.findByNombre(rol).orElseThrow(() ->
                        new NoSuchElementException("Rol " + rol + " no encontrado"))
        ).collect(Collectors.toSet());

        Usuario usuario = usuarioMapper.requestToEntity(request,
                passwordEncoder.encode(request.password()), roles);

        //usuario = usuarioRepository.save(usuario);
        usuarioRepository.save(usuario);
        return usuarioMapper.entityToResponse(usuario);
    }

    @Override
    public UsuarioResponse actualizar(UsuarioRequest request, String username) {
        Usuario usuario = usuarioRepository.findByEstadoRegistroAndUsername(EstadoRegistro.ACTIVO,
                request.username()).orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró usuario con el username proporcionado"));

        usuario.validarPassword(request.password());
        usuario.actualizarPassword(passwordEncoder.encode(request.password()));

        return usuarioMapper.entityToResponse(usuario);
    }

    @Override
    public void eliminar(String username) {
        Usuario usuario = usuarioRepository.findByEstadoRegistroAndUsername(EstadoRegistro.ACTIVO,
                        username).orElseThrow(() -> new NoSuchElementException(
                                "No se encontró el usuario: " + username));
        usuario.eliminadoLogico();
    }
}
