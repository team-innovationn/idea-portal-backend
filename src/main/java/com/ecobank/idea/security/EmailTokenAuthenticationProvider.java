package com.ecobank.idea.security;

import com.ecobank.idea.entity.Role;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.service.TokenVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailTokenAuthenticationProvider implements AuthenticationProvider {
    private final TokenVerificationService tokenVerificationService;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String otp = authentication.getCredentials().toString();

        if (tokenVerificationService.validateOtp(email, otp)) {
            return new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority(Role.USER.toString())));
        }

        throw new BadCredentialsException("Invalid OTP");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
