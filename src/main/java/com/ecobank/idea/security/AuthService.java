package com.ecobank.idea.security;

import com.ecobank.idea.constants.AppConstants;
import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.auth.AuthRequestDTO;
import com.ecobank.idea.dto.auth.AuthResponseDTO;
import com.ecobank.idea.dto.auth.UserRegisterRequestDTO;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // Save User in the DB
    public ResponseDTO registerMultipleUsers(List<UserRegisterRequestDTO> registerRequestDTOS) {
        try {
            for (UserRegisterRequestDTO requestDTO : registerRequestDTOS) {
                // Check if user exists
                if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                    continue;
                }
                // Build user
                User user = new User();
                user.setFirstName(requestDTO.getFirstName());
                user.setLastName(requestDTO.getLastName());
                user.setEmail(requestDTO.getEmail());
                user.setPassword(passwordEncoder.encode((requestDTO.getFirstName().substring(0, 1) + requestDTO.getLastName()).toLowerCase()));
                user.setRole(Role.USER);
                user.setBranch(requestDTO.getBranch());
                user.setDepartment(requestDTO.getDepartment());
                user.setState(requestDTO.getState());
                userRepository.save(user);
            }

        } catch (Exception e) {
            log.error("Error saving account: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return new ResponseDTO(HttpStatus.CREATED, "Users Added to the platform");
    }

    //  Assign JWT to username password user
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));

        // Fetch user
        User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();

        // Generate token
        String token = jwtUtil.generateToken(user);

        // Return token
        return new AuthResponseDTO(token, user, AppConstants.TOKEN_EXPIRATION_TIME);
    }

    // Assign JWT to Oauth User
    public AuthResponseDTO authenticate(String email) {
        // Fetch user
        User user = userRepository.findByEmail(email).orElseThrow();

        // Generate token
        String token = jwtUtil.generateToken(user);

        // Return token
        return new AuthResponseDTO(token, user, AppConstants.TOKEN_EXPIRATION_TIME);
    }
}
