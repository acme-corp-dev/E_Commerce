package com.acme.ecommerce.produits.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IdGenerator {

    /**
     * FAILLE INTENTIONNELLE : `new Random()` non final, et utilisé pour générer
     * des identifiants de produits "uniques" — pas cryptographiquement sûr
     * (prédictible, collisions possibles). Devrait être `SecureRandom` final.
     */
    private Random random = new Random();

    public long nouvelIdProduit() {
        return Math.abs(random.nextLong());
    }
}
