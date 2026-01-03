package com.orderhub.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orderhub.backend.model.UserModel;
import com.orderhub.backend.model.UserModel.Role;
import com.orderhub.backend.repository.UserRepository;
import com.orderhub.backend.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;// Se encarga de autenticar al usuario con su nombre de usuario y contraseña.
    @Autowired
    private PasswordEncoder passwordEncoder; // Codifica las contraseñas antes de guardarlas en la base de datos.
    @Autowired
    private UserRepository userRepository; 

    @Override
    public String login(String email, String password) {

        // Crea un token de autenticación con el nombre de usuario y la contraseña
        var authToken = new UsernamePasswordAuthenticationToken(email, password);

        // Autentica al usuario utilizando el AuthenticationManager
        var authenticate = authenticationManager.authenticate(authToken);

        // Genera y devuelve un token JWT utilizando el nombre de usuario autenticado.
        return JwtUtil.generateToken(((UserDetails) (authenticate.getPrincipal())).getUsername());
    }

     @Override
    public String verifyToken(String token) {
        // Obtiene el nombre de usuario del token.
        var usernameOptional = JwtUtil.getUsernameFromToken(token);
        // Si el token es válido, devuelve el nombre de usuario.
        if (usernameOptional.isPresent()) {
            return usernameOptional.get();
        }
        // Si el token no es válido, lanza una excepción.
        throw new RuntimeException("Token invalid");
    }

    @Override
    public String signUp(String username, String password, String email, Role role) {

        // Verifica si el nombre de usuario ya existe en la base de datos.
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("El Username ya existe");  // Lanza una excepción si el nombre de usuario ya existe.
        }
        // Verificar si el email ya existe
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("El correo electrónico ya existe");
        }
        // Crear un nuevo objeto Usuario
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // La contraseña se cifra antes de guardarse
        user.setEmail(email);
        user.setRol(role);  // Asignar el email

        // Establecer la fecha y hora de registro
        user.setCreatedAt(LocalDateTime.now());
        //user.setEnabled(false); // Asegurarnos que empiece deshabilitado

        user = userRepository.save(user);

        System.out.println("Usuario guardado: " + username);

        return "Verification email sent"; // No devolver JWT hasta verificación
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    @Override
    public void saveUserVerificationToken(UserModel theUser, String verificationToken) {
        throw new UnsupportedOperationException("Unimplemented method 'saveUserVerificationToken'");
    }

    @Override
    public String validateToken(String theToken) {
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }
}
