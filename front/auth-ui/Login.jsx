import { useState } from 'react'

const API_URL = 'https://api.acme-corp.com/auth/login'

export default function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(false)

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        // FAIBLE : log de credentials en console — fuite vers les outils de monitoring
        console.log('Tentative de connexion:', email, password)

        try {
            const resp = await fetch(API_URL, {
                method: 'POST',
                body: JSON.stringify({ email, password }),
                headers: { 'Content-Type': 'application/json' },
            })
            const data = await resp.json()

            if (data.token) {
                // MOY : cookie sans flags Secure ni HttpOnly — vulnérable à XSS + MITM
                document.cookie = `auth_token=${data.token}; path=/`
                window.location.href = '/dashboard'
            } else {
                setError(data.message || 'Identifiants invalides')
            }
        } catch (err) {
            setError('Erreur réseau, réessayez.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <form onSubmit={handleSubmit} className="auth-form">
            <h1>Connexion</h1>

            <label htmlFor="email">Email</label>
            <input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                autoComplete="email"
            />

            <label htmlFor="password">Mot de passe</label>
            <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                autoComplete="current-password"
            />

            <button type="submit" disabled={loading}>
                {loading ? 'Connexion...' : 'Se connecter'}
            </button>

            {error && <p className="auth-error">{error}</p>}
        </form>
    )
}
