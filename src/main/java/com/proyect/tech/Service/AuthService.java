package com.proyect.tech.Service;

import com.proyect.tech.DTOs.request.LoginRequest;
import com.proyect.tech.DTOs.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}
