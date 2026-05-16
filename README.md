# E_Commerce

Projet Maven multi-modules **Spring Boot 2.7 LTS** — référence pour la chaîne
d'analyse sécurité de la plateforme DevSecOps.

> ⚠️ Ce projet contient **des failles intentionnelles** et **des dépendances volontairement
> vulnérables** pour valider que Semgrep, Trivy, Gitleaks et SonarQube remontent
> bien tout l'éventail de findings attendus. **Ne pas déployer en production.**

## Arborescence

```
E_Commerce/
├── pom.xml                         # parent racine (Spring Boot 2.7.18, CycloneDX)
└── backend/                        # module Backend
    ├── pom.xml                     # parent du module + dépendances vulnérables
    ├── auth/                       # SM auth — login, JWT, gestion session
    ├── produits/                   # SM produits — catalogue, recherche, stock
    ├── paiement/                   # SM paiement — Stripe sessions, webhooks
    └── crypto/                     # SM crypto — hash + chiffrement
```

## Failles intentionnelles attendues

### Semgrep / SonarQube (SAST — code)
| Sous-module | Faille |
|---|---|
| `auth` | Injection SQL sur `/login` (concat dans `Statement.executeQuery`) |
| `auth` | Secret JWT hardcodé (`MySuperSecretKey-...`) |
| `auth` | `throws Exception` générique |
| `produits` | Injection SQL sur `/recherche` |
| `produits` | `new Random()` non final pour génération d'identifiants |
| `produits` | Connexion JDBC sans try-with-resources |
| `crypto` | `MessageDigest.getInstance("MD5")` |
| `crypto` | `MessageDigest.getInstance("SHA-1")` |
| `crypto` | `Cipher.getInstance("DES")` + clé en clair |
| `crypto` | Champ privé inutilisé (`legacyMode`) |

### Gitleaks (détection de secrets)
| Sous-module | Secret exposé |
|---|---|
| `paiement` | Clé Stripe `sk_test_…` |
| `paiement` | Clé AWS `AKIA…` + secret key |
| `paiement` | Token GitHub `ghp_…` |

### Trivy (SCA — dépendances)
Versions volontairement anciennes dans `backend/pom.xml` :
- `log4j:log4j:1.2.17` → CVE-2021-4104, CVE-2019-17571
- `com.fasterxml.jackson.core:jackson-databind:2.9.10.4`
- `org.apache.commons:commons-text:1.9` → Text4Shell CVE-2022-42889
- `org.yaml:snakeyaml:1.30` → CVE-2022-1471
- `commons-collections:commons-collections:3.2.1` → CVE-2015-7501
- `com.google.guava:guava:24.1-jre` → CVE-2018-10237, CVE-2023-2976

### SBOM CycloneDX
`mvn cyclonedx:makeAggregateBom` → `bom.json` avec ~15-20 composants.

## Commandes utiles

```bash
# Compiler tout le backend
mvn -f backend/pom.xml clean compile

# Lancer un SM individuel (auth sur 8081, produits 8082, paiement 8083, crypto 8084)
mvn -f backend/auth/pom.xml spring-boot:run

# Générer le SBOM agrégé
mvn package
```
