package com.acme.ecommerce.auth.service;

import org.springframework.stereotype.Service;

@Service
public class LegacyService {

    /**
     * FAILLE INTENTIONNELLE : utilise `throws Exception` générique,
     * ce qui masque la nature des erreurs et empêche un traitement
     * spécifique côté appelant (anti-pattern documenté OWASP).
     */
    public String anciennetraitement(String donnee) throws Exception {
        if (donnee == null) {
            throw new Exception("Donnée nulle non autorisée");
        }
        return donnee.trim().toLowerCase();
    }
}
