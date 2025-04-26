/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsw.sigconbackend.utils;

import dsw.sigconbackend.dto.UsuarioResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Moises_F16.7.24
 */
public class JwtUtil {

    private static final String SECRET_KEY = "claveSuperSecretaParaFirmarElJWT1234567890";

    public static String generarToken(UsuarioResponse user) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("nombre", user.getNombre());
        claims.put("apellidoPaterno", user.getApellidoPaterno());
        claims.put("apellidoMaterno", user.getApellidoMaterno());
        claims.put("correo", user.getCorreo());
        claims.put("empresa", user.getEmpresa());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(claims)
                .setIssuer("app-ccl")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
