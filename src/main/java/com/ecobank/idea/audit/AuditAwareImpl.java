package com.ecobank.idea.audit;

import com.ecobank.idea.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Create bean for Auditing of updates made to records in DB
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    // Get details of user currently logged in trying to perform certain actions
    @Override
    public Optional<String> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = 0;
        if (principal instanceof String) {
            return Optional.of((String) principal);
        }
        return Optional.of(String.valueOf(id));
    }
}