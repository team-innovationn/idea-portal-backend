package com.ecobank.idea.security;

import com.ecobank.idea.constants.AppConstants;
import com.ecobank.idea.dto.auth.AuthRequestDTO;
import com.ecobank.idea.dto.auth.AuthResponseDTO;
import com.ecobank.idea.dto.user.UserResponseDTO;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    //  Assign JWT to username password user
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDTO.getEmail(),
                authRequestDTO.getToken()
        ));

        // Initialize variables
        Authentication finalAuth;
        UserResponseDTO userResponseDTO;

        Optional<User> optionalUser = userRepository.findByEmail(authRequestDTO.getEmail());

        if (optionalUser.isEmpty()) {
            System.out.println("user is empty");
            // For new users, use default LDAP authorities
            finalAuth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    Collections.singletonList(new SimpleGrantedAuthority(Role.USER.toString())) // Default role
            );

            userResponseDTO = UserResponseDTO.builder()
                    .email(authRequestDTO.getEmail())
                    .role(Role.USER)
                    .profileUpdated(false)
                    .build();
        } else {
            User user = optionalUser.get();
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(user.getRole().toString())
            );

            finalAuth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    authorities
            );

            userResponseDTO = UserResponseDTO.builder()
                    .role(user.getRole())
                    .firstName(user.getFirstName())
                    .state(user.getState())
                    .branch(user.getBranch())
                    .lastName(user.getLastName())
                    .department(user.getDepartment())
                    .profileUpdated(user.isProfileUpdated())
                    .email(user.getEmail())
                    .build();
        }

        String token = jwtUtil.generateToken(finalAuth);

        return new AuthResponseDTO(token, userResponseDTO, AppConstants.TOKEN_EXPIRATION_TIME);
    }
}
