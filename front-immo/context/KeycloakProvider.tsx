'use client'; // if using App Router – remove if not

import { createContext, useEffect, useState, ReactNode } from 'react';
import keycloak from '../lib/KeycloakService';

interface AuthContextType {
    keycloak: typeof keycloak | null;
    initialized: boolean;
}

export const AuthContext = createContext<AuthContextType>({
    keycloak: null,
    initialized: false,
});

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [initialized, setInitialized] = useState(false);

    useEffect(() => {
        keycloak.init({ onLoad: 'login-required' }).then(() => {
            setInitialized(true);
        });
    }, []);

    return (
        <AuthContext.Provider value={{ keycloak, initialized }}>
            {initialized ? children : <p>Loading...</p>}
        </AuthContext.Provider>
    );
};