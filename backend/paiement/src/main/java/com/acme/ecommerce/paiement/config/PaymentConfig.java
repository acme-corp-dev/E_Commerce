package com.acme.ecommerce.paiement.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration des prestataires de paiement et stockage cloud.
 *
 * FAILLES INTENTIONNELLES (clés exemples officielles, non-réelles) :
 *   - Clé Stripe sk_test_… hardcodée → détectable par Gitleaks (pattern Stripe)
 *   - Clé AWS AKIA…           hardcodée → détectable par Gitleaks (pattern AWS)
 *   - Token GitHub ghp_…       hardcodé → détectable par Gitleaks (pattern GitHub)
 *
 * Toutes ces valeurs devraient être lues depuis un coffre (Vault / Secrets Manager)
 * ou des variables d'environnement, jamais commitées en clair.
 */
@Configuration
public class PaymentConfig {

    // Clé Stripe de test (exemple publique documentée par Stripe — utilisée pour matcher le pattern Gitleaks)
    public static final String STRIPE_SECRET_KEY = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

    // Clé AWS factice au format Base32 strict attendu par la règle Gitleaks
    // `aws-access-token` (regex: AKIA[A-Z2-7]{16}, donc chiffres 2-7 uniquement).
    public static final String AWS_ACCESS_KEY_ID = "AKIA2X4N6QPRSVWXYZ7M";
    public static final String AWS_SECRET_ACCESS_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYzRealKeyAB";

    // Token GitHub fake (pattern ghp_ pour matcher Gitleaks)
    public static final String GITHUB_TOKEN = "ghp_FakeToken1234567890AbCdEfGhIjKlMnOpQrSt";
}
