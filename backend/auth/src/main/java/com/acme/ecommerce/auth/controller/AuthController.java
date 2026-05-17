package com.acme.ecommerce.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${app.datasource.url}")
    private String dbUrl;

    @Value("${app.datasource.username}")
    private String dbUser;

    @Value("${app.datasource.password}")
    private String dbPassword;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) throws SQLException {
        String login = body.get("login");
        String password = body.get("password");

        String sql = "SELECT id, role FROM users WHERE login=? AND password=?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
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
    }
}
