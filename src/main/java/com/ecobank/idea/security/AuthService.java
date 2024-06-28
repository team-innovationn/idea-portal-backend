package com.ecobank.idea.security;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // Save User in the DB
    public User register(UserRegisterRequestDTO request) {

        // Check if user exists
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (userExists) {
            throw new UserAlreadyExistsException("User " + request.getEmail() + " already exists");
        }

        // Retrieve user department
        Department department = departmentRepository.findById(Long.valueOf(request.getDepartmentId())).orElseThrow(() -> {
            throw new ResourceNotFoundException("Department selected not found");
        });

        // Build user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        user.setBranch(request.getBranch());
        user.setDepartment(department);
        user.setState(request.getState());

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
