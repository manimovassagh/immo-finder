import './globals.css'
import { ReactNode } from 'react'
import { AuthProvider } from '@/components/KeycloakProvider'
import { Navbar } from '@/components/Navbar'

export const metadata = {
    title: 'Front Immo',
    description: 'Real estate platform',
}

export default function RootLayout({ children }: { children: ReactNode }) {
    return (
        <html lang="en">
        <body>
        <AuthProvider>
            <Navbar />
        </AuthProvider>
        </body>
        </html>
    )
}