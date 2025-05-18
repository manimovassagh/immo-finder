'use client';

import { useAuth } from './KeycloakProvider';
import { User, Settings } from 'lucide-react';
import * as DropdownMenu from '@radix-ui/react-dropdown-menu';

export function UserMenu() {
  const { isAuthenticated, login, logout, keycloak } = useAuth();

  const openUserManagement = () => {
    keycloak.accountManagement();
  };

  if (!isAuthenticated) {
    return (
      <button
        onClick={login}
        className="outline-none"
        title="Sign in"
      >
        <div className="w-8 h-8 rounded-full flex items-center justify-center bg-gray-200 text-gray-600">
          <User className="w-5 h-5" />
        </div>
      </button>
    );
  }

  return (
    <DropdownMenu.Root>
      <DropdownMenu.Trigger asChild>
        <button className="outline-none">
          <div className="w-8 h-8 rounded-full flex items-center justify-center bg-blue-500 text-white">
            <User className="w-5 h-5" />
          </div>
        </button>
      </DropdownMenu.Trigger>

      <DropdownMenu.Portal>
        <DropdownMenu.Content
          className="min-w-[200px] bg-white rounded-lg shadow-lg p-2 mt-2"
          sideOffset={5}
        >
          <DropdownMenu.Item className="outline-none">
            <div className="px-3 py-2 text-sm text-gray-600">
              Signed in as
              <div className="font-medium text-gray-900">
                {keycloak.tokenParsed?.preferred_username || 'User'}
              </div>
            </div>
          </DropdownMenu.Item>

          <DropdownMenu.Separator className="h-px bg-gray-200 my-1" />

          <DropdownMenu.Item className="outline-none">
            <button
              onClick={openUserManagement}
              className="w-full px-3 py-2 text-left text-sm text-gray-600 hover:bg-gray-50 rounded inline-flex items-center gap-2"
            >
              <Settings className="w-4 h-4" />
              User Management
            </button>
          </DropdownMenu.Item>

          <DropdownMenu.Item className="outline-none">
            <button
              onClick={logout}
              className="w-full px-3 py-2 text-left text-sm text-red-600 hover:bg-red-50 rounded"
            >
              Sign out
            </button>
          </DropdownMenu.Item>
        </DropdownMenu.Content>
      </DropdownMenu.Portal>
    </DropdownMenu.Root>
  );
}
