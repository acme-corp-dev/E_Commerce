package com.acme.ecommerce.crypto.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    /**
     * FAILLE INTENTIONNELLE : MD5 utilisé pour hasher des données sensibles.
     * MD5 est cryptographiquement cassé (collisions triviales) depuis 2004,
     * ne doit JAMAIS être utilisé pour de l'authentification ni de la signature.
     * Préférer SHA-256 / SHA-3 / bcrypt selon le besoin.
     */
    public String hashUtilisateur(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * FAILLE INTENTIONNELLE BIS : SHA1, également déprécié (collisions
     * démontrées en 2017 par Google). Détecté par Semgrep et SonarQube.
     */
    public String hashAncien(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return new String(md.digest(input.getBytes(StandardCharsets.UTF_8)));
    }
}
