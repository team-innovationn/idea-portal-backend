package com.ecobank.idea.service;

public interface EmailService {
    void sendVerificationEmail(String email, Object payload);
}
