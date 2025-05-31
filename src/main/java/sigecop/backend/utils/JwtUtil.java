/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.utils;

import sigecop.backend.security.dto.PaginaResponse;
import sigecop.backend.security.dto.UsuarioResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

import java.security.Key;
import java.util.*;

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
        List<Map<String, Object>> pages = new ArrayList<>();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("nombre", user.getNombre());
        claims.put("apellidoPaterno", user.getApellidoPaterno());
        claims.put("apellidoMaterno", user.getApellidoMaterno());
        claims.put("perfil", user.getRol().getNombre());
        for (PaginaResponse pag:user.getPaginas()){
            Map<String, Object> pagAdd = new HashMap<>();
            pagAdd.put("url", pag.getUrl());
            pagAdd.put("nombre", pag.getNombre());
            pages.add(pagAdd);
        }
        claims.put("pages", pages);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(claims)
                .setIssuer("app-sigecop")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7200000)) //1800000 media hora
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
