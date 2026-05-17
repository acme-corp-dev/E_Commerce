package com.acme.ecommerce.auth.controller;

import com.acme.ecommerce.auth.entity.Utilisateur;
import com.acme.ecommerce.auth.repository.UtilisateurRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;

    public AuthController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        String login = body.get("login");
        String motDePasseClair = body.get("password");

        Map<String, Object> result = new HashMap<>();
        if (login == null || motDePasseClair == null) {
            result.put("authenticated", false);
            return result;
        }

        Optional<Utilisateur> opt = utilisateurRepository.findByLogin(login);
        if (opt.isEmpty()) {
            result.put("authenticated", false);
            return result;
        }

        Utilisateur user = opt.get();
        String hashEntrant = hash(motDePasseClair);
        if (!hashEntrant.equals(user.getPasswordHash())) {
            result.put("authenticated", false);
            return result;
        }

        result.put("id", user.getId());
        result.put("role", user.getRole());
        result.put("authenticated", true);
        return result;
    }

    private static String hash(String entree) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(entree.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
