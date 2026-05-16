package com.acme.ecommerce.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    /**
     * FAILLE INTENTIONNELLE : secret JWT hardcodé dans le code source.
     * Un attaquant qui accède au repo peut forger n'importe quel token.
     * À remplacer par une lecture depuis Vault / variable d'environnement.
     */
    private static final String JWT_SECRET = "MySuperSecretKey-DO-NOT-PUSH-2026";

    private static final long EXPIRATION_MS = 3600_000L;

    public String genererToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}
