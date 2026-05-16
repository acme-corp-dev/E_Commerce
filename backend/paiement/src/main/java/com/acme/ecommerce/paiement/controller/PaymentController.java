package com.acme.ecommerce.paiement.controller;

import com.acme.ecommerce.paiement.config.PaymentConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paiement")
public class PaymentController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> out = new HashMap<>();
        out.put("status", "ok");
        out.put("provider", "stripe");
        // Volontairement on n'expose pas les clés ici, mais le code de config les contient
        // (sera détecté par Gitleaks au scan du repo).
        out.put("hasStripeKey", PaymentConfig.STRIPE_SECRET_KEY != null);
        return out;
    }
}
