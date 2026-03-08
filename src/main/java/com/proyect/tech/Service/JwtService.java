package com.proyect.tech.Service;

import com.proyect.tech.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Esta llave debe ser secreta y tener al menos 32 caracteres. 
    // En producción, llévala al application.yml
    private static final String SECRET_KEY = "mi_clave_secreta_super_segura_y_muy_larga_123456789";

    // 1. GENERAR EL TOKEN
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // Identificador del usuario
                .claim("role", user.getRole()) // Puedes meter datos extra
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira en 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. EXTRAER EL EMAIL DEL TOKEN (Para validar quién es)
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. VALIDAR SI EL TOKEN ES VÁLIDO
    public boolean isTokenValid(String token, String userEmail) {
        final String email = extractEmail(token);
        return (email.equals(userEmail)) && !isTokenExpired(token);
    }

    // --- Métodos auxiliares internos ---

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}