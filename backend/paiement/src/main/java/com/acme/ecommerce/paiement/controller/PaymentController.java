package com.acme.ecommerce.paiement.controller;

import com.acme.ecommerce.paiement.config.PaymentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paiement")
public class PaymentController {

    @Autowired
    private PaymentConfig paymentConfig;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> out = new HashMap<>();
        out.put("status", "ok");
        out.put("provider", "stripe");
        out.put("hasStripeKey", paymentConfig.hasStripeKey());
        return out;
    }
}
