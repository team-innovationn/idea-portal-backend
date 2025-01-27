//package com.ecobank.idea.config;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Collections;
//
//@Configuration
//public class OauthTokenConfig {
//
//    @Bean
//    public GoogleIdTokenVerifier googleIdTokenVerifier(ApplicationProperties applicationProperties) {
//        return new GoogleIdTokenVerifier.Builder(
//                new NetHttpTransport(),
//                new GsonFactory()
//        )
//                .setAudience(Collections.singletonList(applicationProperties.getGoogleClientId()))
//                .build();
//    }
//
////    @Bean
////    public JwtConsumer jwtConsumer(ApplicationProperties applicationProperties) {
////        return new JwtConsumerBuilder()
////                .setExpectedAudience(applicationProperties.getMicrosoftClientId())
////                .setExpectedIssuer(applicationProperties.getMicrosoftIssuer())
////                .setSkipDefaultAudienceValidation()
////                .setVerificationKeyResolver(getJwksVerificationKey())
////                .build();
////    }
////
////    private VerificationKeyResolver getJwksVerificationKey() {
////        HttpsJwksVerificationKeyResolver keyResolver;
////        try {
////            HttpsJwks httpsJwks = new HttpsJwks("https://login.microsoftonline.com/common/discovery/v2.0/keys");
////            keyResolver = new HttpsJwksVerificationKeyResolver(httpsJwks);
////        } catch (Exception e) {
////            throw new RuntimeException("Failed to get Microsoft JWKS key", e);
////        }
////        return keyResolver;
////    }
//}
