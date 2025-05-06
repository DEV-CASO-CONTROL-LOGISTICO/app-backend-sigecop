/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.utils;

import ccl.securitybackend.security.dto.UsuarioResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Moises_F16.7.24
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(UsuarioResponse user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("nombre", user.getNombre());
        claims.put("apellidoPaterno", user.getApellidoPaterno());
        claims.put("apellidoMaterno", user.getApellidoMaterno());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(claims)
                .setIssuer("app-ccl")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) //1800000 media hora 
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
