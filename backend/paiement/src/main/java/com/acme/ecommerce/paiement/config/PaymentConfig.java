package com.acme.ecommerce.paiement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des prestataires de paiement et stockage cloud.
 * Toutes les clés sont injectées depuis des variables d'environnement
 * (Vault / Secrets Manager en prod). Aucune valeur sensible n'est en clair
 * dans le code source.
 */
@Configuration
public class PaymentConfig {

    @Value("${stripe.secret-key:}")
    private String stripeSecretKey;

    @Value("${aws.access-key-id:}")
    private String awsAccessKeyId;

    @Value("${aws.secret-access-key:}")
    private String awsSecretAccessKey;

    @Value("${github.token:}")
    private String githubToken;

    public boolean hasStripeKey() {
        return stripeSecretKey != null && !stripeSecretKey.isBlank();
    }

    public boolean hasAwsCredentials() {
        return awsAccessKeyId != null && !awsAccessKeyId.isBlank()
            && awsSecretAccessKey != null && !awsSecretAccessKey.isBlank();
    }

    public boolean hasGithubToken() {
        return githubToken != null && !githubToken.isBlank();
    }
}
