'use client';

import { ReactNode, createContext, useContext, useEffect, useState } from 'react';
import Keycloak, { KeycloakInstance } from 'keycloak-js';


const keycloak = new Keycloak({
  url: process.env.NEXT_PUBLIC_KEYCLOAK_URL!,
  realm: process.env.NEXT_PUBLIC_KEYCLOAK_REALM!,
  clientId: process.env.NEXT_PUBLIC_KEYCLOAK_CLIENT_ID!,
});

interface AuthContextType {
  keycloak: KeycloakInstance;
  initialized: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    keycloak
        .init({ onLoad: 'login-required' })
        .then((authenticated) => {
          if (authenticated) {
            setInitialized(true);
          } else {
            window.location.reload();
          }
        })
        .catch(() => window.location.reload());
  }, []);

  return (
      <AuthContext.Provider value={{ keycloak, initialized }}>
        {children}
      </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}