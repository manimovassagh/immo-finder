import { useContext } from 'react';
import { AuthContext } from '@/context/KeycloakProvider';

export default function Home() {
    const { keycloak, initialized } = useContext(AuthContext);

    if (!initialized || !keycloak) return <p>Loading...</p>;

    const username = keycloak.tokenParsed?.preferred_username;
    const email = keycloak.tokenParsed?.email;

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <img
                    src={`https://api.dicebear.com/7.x/initials/svg?seed=${username}`}
                    alt="avatar"
                    style={styles.avatar}
                />
                <h1 style={styles.heading}>Welcome, {username}</h1>
                <p style={styles.email}>{email}</p>

                <button onClick={() => keycloak.logout()} style={styles.logout}>
                    Logout
                </button>
            </div>
        </div>
    );
}

const styles = {
    container: {
        height: '100vh',
        background: '#f3f4f6',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
    },
    card: {
        background: 'white',
        padding: '2rem',
        borderRadius: '1rem',
        boxShadow: '0 10px 20px rgba(0,0,0,0.1)',
        textAlign: 'center' as const,
        minWidth: '300px',
    },
    avatar: {
        width: 80,
        height: 80,
        borderRadius: '50%',
        marginBottom: '1rem',
    },
    heading: {
        fontSize: '1.5rem',
        margin: 0,
    },
    email: {
        color: '#4b5563',
        marginBottom: '1.5rem',
    },
    logout: {
        backgroundColor: '#ef4444',
        border: 'none',
        padding: '0.6rem 1.2rem',
        borderRadius: '0.5rem',
        color: 'white',
        cursor: 'pointer',
        fontWeight: 500,
    },
};