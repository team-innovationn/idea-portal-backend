package com.ecobank.idea.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class GoogleTokenConfig {

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(ApplicationProperties applicationProperties) {
        return new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory()
        )
                .setAudience(Collections.singletonList(applicationProperties.getGoogleClientId()))
                .build();
    }
}
