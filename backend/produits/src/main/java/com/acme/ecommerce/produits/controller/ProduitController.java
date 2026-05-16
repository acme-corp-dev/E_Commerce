package com.acme.ecommerce.produits.controller;

import com.acme.ecommerce.produits.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository repository;

    /**
     * Recherche de produits par nom.
     * FAILLE INTENTIONNELLE : la requête `nom` est interpolée directement dans le SQL
     * sans paramétrage → injection SQL classique.
     */
    @GetMapping("/recherche")
    public List<Map<String, Object>> rechercher(@RequestParam("nom") String nom) throws SQLException {
        return repository.rechercherParNom(nom);
    }
}
