package com.ecobank.idea.service.impl;

import com.ecobank.idea.config.ApplicationProperties;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class TokenVerificationService {
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final ApplicationProperties applicationProperties;

    public String verifyGoogleToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = googleIdTokenVerifier.verify(token);
        if (idToken == null) {
            throw new AccessDeniedException("Invalid Google Token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        return payload.getEmail();
    }
}
