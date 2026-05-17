package com.acme.ecommerce.produits.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private BigDecimal prix;

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public BigDecimal getPrix() { return prix; }

    public void setId(Long id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
}
