import { useContext } from 'react';
import { AuthContext } from '@/context/KeycloakProvider';

export default function Home() {
    const { keycloak, initialized } = useContext(AuthContext);

    if (!initialized || !keycloak) return <p>Loading...</p>;

    const username = keycloak.tokenParsed?.preferred_username;
    const email = keycloak.tokenParsed?.email;

    return (
        <div className="h-screen bg-gray-100 flex justify-end items-start p-8">
            <div className="bg-white p-6 rounded-xl shadow-lg text-center min-w-[250px]">
                <img
                    src={`https://api.dicebear.com/7.x/initials/svg?seed=${username}`}
                    alt="avatar"
                    className="w-20 h-20 rounded-full mb-4 mx-auto"
                />
                <h1 className="text-xl font-semibold mb-1">Welcome, {username}</h1>
                <p className="text-gray-600 mb-6">{email}</p>
                <button
                    onClick={() => keycloak.logout()}
                    className="bg-red-500 hover:bg-red-600 text-white font-medium py-2 px-4 rounded"
                >
                    Logout
                </button>
            </div>
        </div>
    );
}