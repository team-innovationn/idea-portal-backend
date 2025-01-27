//package com.ecobank.idea.service.impl;
//
//import com.ecobank.idea.config.ApplicationProperties;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//@Service
//@RequiredArgsConstructor
//public class TokenVerificationService {
//    private final GoogleIdTokenVerifier googleIdTokenVerifier;
//    private final ApplicationProperties applicationProperties;
////    private final JwtConsumer microsoftVerifier;
//
//    public String verifyGoogleToken(String token) throws GeneralSecurityException, IOException {
//        GoogleIdToken idToken = googleIdTokenVerifier.verify(token);
//        if (idToken == null) {
//            throw new AccessDeniedException("Invalid Google Token");
//        }
//
//        GoogleIdToken.Payload payload = idToken.getPayload();
//        return payload.getEmail();
//    }
//
////    public UserInfo verifyMicrosoftToken(String idToken) throws Exception {
////        JwtClaims claims = microsoftVerifier.processToClaims(idToken);
////        System.out.println(claims);
////        return UserInfo.builder()
////                .email(claims.getStringClaimValue("email"))
////                .name(claims.getStringClaimValue("name"))
////                .build();
////    }
//}
