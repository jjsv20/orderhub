package com.orderhub.backend.util;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.time.DateUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JwtUtil {
    private static SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private static final String ISSUER = "server";


    private JwtUtil() {}

    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }
    public static Optional<Claims> parseToken(String jwtToken){
        var jwtParser = Jwts.parser()
            .verifyWith(secretKey)
            .build();
    
        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken).getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            System.err.print("JWT Exception ocurred: " + e.getMessage());
            return Optional.empty();
        }
    }
    public static Optional<String> getUsernameFromToken(String jwtToken) {
        var claimsOptional = parseToken(jwtToken); // Obtiene los Claims del token.
        return claimsOptional.map(Claims::getSubject); // Extrae el subject (nombre de usuario) si los Claims están presentes.
    }

    public static String generateToken(String username) {
    // Obtiene la fecha actual para establecer los campos issuedAt y expiration.
    var currentDate = new Date();
    // Define el tiempo de expiración del token en minutos.
    var jwtExpirationInMinutes = 10;
    // Calcula la fecha de expiración sumando los minutos definidos a la fecha actual.
    var expiration = DateUtils.addMinutes(currentDate, jwtExpirationInMinutes);
    // Construye y firma el token JWT.
    return Jwts.builder()
        .id(UUID.randomUUID().toString()) // Asigna un identificador único al token.
        .issuer(ISSUER) // Establece el emisor del token.
        .subject(username) // Establece el nombre de usuario como subject del token.
        .signWith(secretKey) // Firma el token con la clave secreta.
        .issuedAt(currentDate) // Establece la fecha en que el token fue emitido.
        .expiration(expiration) // Establece la fecha de expiración del token.
        .compact(); // Genera el token en formato string compacto.
    }
}