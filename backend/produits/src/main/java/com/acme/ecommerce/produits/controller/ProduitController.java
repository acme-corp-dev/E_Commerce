package com.acme.ecommerce.produits.controller;

import com.acme.ecommerce.produits.entity.Produit;
import com.acme.ecommerce.produits.repository.ProduitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitRepository repository;

    public ProduitController(ProduitRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recherche")
    public List<Produit> rechercher(@RequestParam("nom") String nom) {
        return repository.findByNomContainingIgnoreCase(nom);
    }
}
