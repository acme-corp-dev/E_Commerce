package com.acme.ecommerce.auth.service;

import org.springframework.stereotype.Service;

@Service
public class LegacyService {

    public String anciennetraitement(String donnee) {
        if (donnee == null) {
            throw new IllegalArgumentException("Donnée nulle non autorisée");
        }
        return donnee.trim().toLowerCase();
    }
}
