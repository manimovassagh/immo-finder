import './globals.css'
import { ReactNode } from 'react'
import { AuthProvider } from '@/components/KeycloakProvider'

export const metadata = {
    title: 'Front Immo',
    description: 'Real estate platform',
}

export default function RootLayout({ children }: { children: ReactNode }) {
    return (
        <html lang="en">
        <body>
        <AuthProvider>
            {children}
        </AuthProvider>
        </body>
        </html>
    )
}