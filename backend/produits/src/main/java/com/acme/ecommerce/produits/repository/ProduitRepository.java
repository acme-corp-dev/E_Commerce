package com.acme.ecommerce.produits.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProduitRepository {

    @Value("${app.datasource.url}")
    private String dbUrl;

    @Value("${app.datasource.username}")
    private String dbUser;

    @Value("${app.datasource.password}")
    private String dbPassword;

    public List<Map<String, Object>> rechercherParNom(String nom) throws SQLException {
        List<Map<String, Object>> out = new ArrayList<>();
        String sql = "SELECT id, nom, prix FROM produits WHERE nom LIKE ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("nom", rs.getString("nom"));
                    row.put("prix", rs.getBigDecimal("prix"));
                    out.add(row);
                }
            }
        }
        return out;
    }
}
