package com.ecobank.idea.repository;

import com.ecobank.idea.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByEmail(String email);

    Optional<VerificationToken> findByEmailAndTokenAndUsedFalseAndExpiryTimeAfter(String email, String otp, LocalDateTime now);
}
