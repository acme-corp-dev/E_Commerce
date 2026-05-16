package com.acme.ecommerce.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Endpoint de connexion utilisateur.
     * FAILLE INTENTIONNELLE : injection SQL via concaténation directe du login.
     * Un payload login="admin' OR '1'='1" contourne l'authentification.
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) throws SQLException {
        String login = body.get("login");
        String password = body.get("password");

        Connection conn = DriverManager.getConnection("jdbc:h2:mem:auth", "sa", "");
        Statement stmt = conn.createStatement();
        String sql = "SELECT id, role FROM users WHERE login='" + login
                + "' AND password='" + password + "'";
        ResultSet rs = stmt.executeQuery(sql);

        Map<String, Object> result = new HashMap<>();
        if (rs.next()) {
            result.put("id", rs.getInt("id"));
            result.put("role", rs.getString("role"));
            result.put("authenticated", true);
        } else {
            result.put("authenticated", false);
        }
        return result;
    }
}
