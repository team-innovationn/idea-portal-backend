package com.ecobank.idea.security;

import com.ecobank.idea.dto.auth.AuthRequestDTO;
import com.ecobank.idea.dto.auth.AuthResponseDTO;
import com.ecobank.idea.dto.auth.UserRegisterRequestDTO;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.exception.UserAlreadyExistsException;
import com.ecobank.idea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // Save User in the DB
    public User register(UserRegisterRequestDTO registerRequestDTO) {
        // Check if user exists
        boolean userExists = userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent();
        if (userExists) {
            throw new UserAlreadyExistsException("User " + registerRequestDTO.getEmail() + " already exists");
        }

        // Build user
        User user = new User();
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(Role.USER);
        user.setBranch(registerRequestDTO.getBranch());
        user.setDepartment(registerRequestDTO.getDepartment());
        user.setState(registerRequestDTO.getState());

        // Save user to repository
        User userSaved = userRepository.save(user);

        // Check if user is saved
        if (userSaved.getUserId() < 0 && null == userSaved) {
            throw new RuntimeException("Unable to save User");
        }

        return userSaved;
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {

        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));

        // Fetch user
        User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();

        // Generate token
        String jwtToken = jwtUtil.generateToken(user);

        // Return token
        return new AuthResponseDTO(jwtToken);
    }
}
