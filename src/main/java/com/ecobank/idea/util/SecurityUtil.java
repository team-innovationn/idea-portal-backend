package com.ecobank.idea.util;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    // Prevent instantiation
    private SecurityUtil() {
    }

    // Get currently logged-in user from spring context
    public static Object getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
}
