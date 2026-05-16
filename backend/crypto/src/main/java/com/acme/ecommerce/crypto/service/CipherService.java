package com.acme.ecommerce.crypto.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CipherService {

    /**
     * FAILLES INTENTIONNELLES :
     *   1. Algorithme DES utilisé pour le chiffrement — cassé depuis les années 2000
     *      (clé 56 bits, force brute en quelques heures). Préférer AES-256-GCM.
     *   2. Clé DES en clair dans le code source ("12345678") — devrait être
     *      stockée dans un coffre et tournée régulièrement.
     *   3. Champ privé inutilisé (`legacyMode`) — code mort, à supprimer.
     */
    private static final byte[] DES_KEY = "12345678".getBytes(StandardCharsets.UTF_8);

    private boolean legacyMode = false;  // champ privé inutilisé

    public String chiffrer(String plain) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(DES_KEY, "DES"));
        byte[] encrypted = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
