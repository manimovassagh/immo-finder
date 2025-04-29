import type { AppProps } from 'next/app';
import { AuthProvider } from '@/context/KeycloakProvider';

export default function App({ Component, pageProps }: AppProps) {
    return (
        <AuthProvider>
            <Component {...pageProps} />
        </AuthProvider>
    );
}