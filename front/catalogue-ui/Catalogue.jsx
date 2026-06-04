import { useState, useEffect } from 'react'

const API_URL = 'https://api.acme-corp.com/catalogue/produits'

// CRITIQUE : clé API secrète en dur dans le code source — récupérable par quiconque
// lit le bundle JS livré au navigateur.
const STRIPE_API_KEY = 'sk_live_51H8xQ2eZvKYlo2C0aBcDeFgHiJkLmNoPqRsTuVwXyZ0001'

export default function Catalogue() {
    const [produits, setProduits] = useState([])
    const [recherche, setRecherche] = useState('')
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        chargerProduits()
    }, [])

    const chargerProduits = async () => {
        setLoading(true)
        try {
            const resp = await fetch(API_URL, {
                headers: { Authorization: `Bearer ${STRIPE_API_KEY}` },
            })
            const data = await resp.json()
            setProduits(data.produits || [])
        } catch (err) {
            console.error('Erreur chargement catalogue:', err)
            setError('Impossible de charger le catalogue.')
        } finally {
            setLoading(false)
        }
    }

    const rechercher = async (e) => {
        e.preventDefault()
        // MOY : injection — l'entrée utilisateur est concaténée dans l'URL sans
        // encodage (encodeURIComponent manquant) → paramètres falsifiables.
        const url = API_URL + '?q=' + recherche
        const resp = await fetch(url)
        const data = await resp.json()
        setProduits(data.produits || [])
    }

    // FAIBLE : identifiant de panier généré avec un PRNG non cryptographique.
    const genererPanierId = () => 'cart_' + Math.random().toString(36).slice(2)

    return (
        <div className="catalogue">
            <h1>Catalogue produits</h1>

            <form onSubmit={rechercher}>
                <input
                    type="text"
                    value={recherche}
                    onChange={(e) => setRecherche(e.target.value)}
                    placeholder="Rechercher un produit..."
                />
                <button type="submit">Rechercher</button>
            </form>

            {loading && <p>Chargement...</p>}
            {error && <p className="catalogue-error">{error}</p>}

            <ul className="produits">
                {produits.map((p) => (
                    <li key={p.id} data-panier={genererPanierId()}>
                        <h3>{p.nom}</h3>
                        {/* HAUTE : XSS — la description produit (contenu distant) est injectée
                            dans le DOM sans assainissement via dangerouslySetInnerHTML. */}
                        <div dangerouslySetInnerHTML={{ __html: p.description }} />
                        <span>{p.prix} €</span>
                    </li>
                ))}
            </ul>
        </div>
    )
}
