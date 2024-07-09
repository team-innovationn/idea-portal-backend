package com.ecobank.idea.security;

import com.ecobank.idea.constants.AppConstants;
import com.ecobank.idea.dto.auth.AuthRequestDTO;
import com.ecobank.idea.dto.auth.AuthResponseDTO;
import com.ecobank.idea.dto.auth.UserRegisterRequestDTO;
import com.ecobank.idea.entity.Department;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.exception.UserAlreadyExistsException;
import com.ecobank.idea.repository.DepartmentRepository;
import com.ecobank.idea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // Save User in the DB
    public User register(UserRegisterRequestDTO request) {
        // Check if user exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User " + request.getEmail() + " already exists");
        }

        // Retrieve user department
        Department department = departmentRepository.findById(Long.valueOf(request.getDepartmentId()))
                .orElseThrow(() -> new ResourceNotFoundException("Department selected not found"));

        // Build user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setBranch(request.getBranch());
        user.setDepartment(department);
        user.setState(request.getState());

        // Save user to repository
        User userSaved = null;
        try {
            userSaved = userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to save User " + e.getMessage());
        }
        return userSaved;
    }

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
}
