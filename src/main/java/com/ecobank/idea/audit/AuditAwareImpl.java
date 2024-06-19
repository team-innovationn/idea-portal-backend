package com.ecobank.idea.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Create bean for Auditing of updates made to records in DB
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    // Get details of user currently logged in trying to perform certain actions
    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: Retrieve current user from security context
        return Optional.of("ADMIN");
    }
}