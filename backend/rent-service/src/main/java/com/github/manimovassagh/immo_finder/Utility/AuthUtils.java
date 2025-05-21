package com.github.manimovassagh.immo_finder.Utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtils {

    public UUID getCurrentUserId() {
        // Later: extract from SecurityContext using Keycloak
        // For now: return fixed mock ID
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}