package com.acme.ecommerce.crypto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

/**
 * Chiffrement symétrique AES via Spring Security Crypto.
 * Password + salt injectés depuis l'environnement (jamais en clair dans le code).
 * Encryptors.delux utilise AES-256 + PBKDF2 pour dériver la clé.
 */
@Service
public class CipherService {

    private final TextEncryptor encryptor;

    public CipherService(@Value("${crypto.password}") String password,
                         @Value("${crypto.salt}") String saltHex) {
        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                "crypto.password manquant. Définir via variable d'environnement."
            );
        }
        if (saltHex == null || saltHex.isBlank()) {
            throw new IllegalStateException(
                "crypto.salt manquant. Définir via variable d'environnement (hex)."
            );
        }
        this.encryptor = Encryptors.delux(password, saltHex);
    }

    public String chiffrer(String plain) {
        return encryptor.encrypt(plain);
    }

    public String dechiffrer(String chiffre) {
        return encryptor.decrypt(chiffre);
    }
}
