package com.acme.ecommerce.produits.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class IdGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public long nouvelIdProduit() {
        return Math.abs(RANDOM.nextLong());
    }
}
