package com.damoi.auth.services;

import com.damoi.auth.dto.LoginRequest;
import com.damoi.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}

