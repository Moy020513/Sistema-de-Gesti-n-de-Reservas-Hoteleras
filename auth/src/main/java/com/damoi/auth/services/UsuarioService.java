package com.damoi.auth.services;

import java.util.Set;

import com.damoi.auth.dto.UsuarioRequest;
import com.damoi.auth.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse actualizar(UsuarioRequest request, String username);

    UsuarioResponse eliminar(String username);
}
