'use client';

import { useAuth } from '@/components/KeycloakProvider';
import { useState } from 'react';

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Button } from '@/components/ui/button';

export default function HomePage() {
  const { keycloak, initialized } = useAuth();

  if (!initialized) return <p className="p-4">Loading...</p>;

  const username = keycloak.tokenParsed?.preferred_username ?? 'user';

  return (
      <div className="min-h-screen bg-gray-50 p-6">
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-xl font-bold text-gray-800">Front Immo</h1>

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" className="p-0 rounded-full hover:bg-gray-100">
                <img
                    src={`https://api.dicebear.com/7.x/initials/svg?seed=${username}&size=64`}
                    alt="avatar"
                    className="w-10 h-10 rounded-full border border-gray-300 shadow-sm"
                />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem onClick={() => console.log('Overview')}>
                Overview
              </DropdownMenuItem>
              <DropdownMenuItem onClick={() => console.log('Settings')}>
                Settings
              </DropdownMenuItem>
              <DropdownMenuItem
                  onClick={() => keycloak.logout()}
                  className="text-red-600 focus:bg-red-100"
              >
                Logout
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
  );
}