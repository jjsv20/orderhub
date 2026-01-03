package com.orderhub.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderhub.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/login") // Define que este método manejará solicitudes POST a "/api/auth/login".
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {

        try {
            // Llama al servicio para autenticar al usuario y generar un token JWT
            var jwtToken = authService.login(authRequestDto.email(), authRequestDto.password());

            // Crea un objeto de respuesta con el token y el estado de éxito
            var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS, "Inicio de sesion exitoso");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(authResponseDto);

        } catch (Exception e) {
            String errorMessage = e.getMessage();

            AuthStatus status = AuthStatus.LOGIN_FAILED;
            e.printStackTrace();

            if (e.getMessage().contains("Bad credentials")) {
                errorMessage = "Uuairo o contraseña incorrectas";
            } else if (e.getMessage().contains("User not found")) {
                errorMessage = "No usuario no encontrado";
            }

            var authResponseDto = new AuthResponseDto(null, status, errorMessage);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(authResponseDto);
        }

    }

    @PostMapping("/registrar") // Define que este método manejará solicitudes POST
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto) {
        try {
            // Llama al servicio para registrar al usuario y generar un token JWT.
            var jwtToken = authService.signUp(authRequestDto.username(),
                    authRequestDto.password(), authRequestDto.email(), authRequestDto.role());

            // Crea un objeto de respuesta con el token y el estado de éxito.
            var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.USER_CREATED_SUCCESSFULLY, "vvv");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(authResponseDto);

        } catch (Exception e) {
            String errorMessage = e.getMessage();

            AuthStatus status = AuthStatus.USER_NOT_CREATED;
            e.printStackTrace();

            if (e.getMessage().contains("Username already exists")) {
                errorMessage = "El nombnre de usuario ya esta en usado";
            } else if (e.getMessage().contains("Email already exists")) {
                errorMessage = "El correo electronico ya esta registradp";
            }

            var authResponseDto = new AuthResponseDto(null, status, errorMessage);

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(authResponseDto);
        }
    }
}
