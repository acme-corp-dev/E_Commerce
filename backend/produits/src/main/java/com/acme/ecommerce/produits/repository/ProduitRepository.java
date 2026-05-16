package com.acme.ecommerce.produits.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProduitRepository {

    /**
     * FAILLE INTENTIONNELLE :
     *  1) Concaténation SQL → injection
     *  2) Connection / Statement / ResultSet ouverts sans try-with-resources
     *     → fuites de ressources si exception en cours d'exécution.
     */
    public List<Map<String, Object>> rechercherParNom(String nom) throws SQLException {
        List<Map<String, Object>> out = new ArrayList<>();

        Connection conn = DriverManager.getConnection("jdbc:h2:mem:produits", "sa", "");
        Statement stmt = conn.createStatement();
        String sql = "SELECT id, nom, prix FROM produits WHERE nom LIKE '%" + nom + "%'";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", rs.getLong("id"));
            row.put("nom", rs.getString("nom"));
            row.put("prix", rs.getBigDecimal("prix"));
            out.add(row);
        }
        return out;
    }
}
