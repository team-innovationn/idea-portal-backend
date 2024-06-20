package com.ecobank.idea.controller;

import com.ecobank.idea.config.security.AuthService;
import com.ecobank.idea.dto.AuthRequestDTO;
import com.ecobank.idea.dto.AuthResponseDTO;
import com.ecobank.idea.dto.UserRegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody UserRegisterRequestDTO registerRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.register(registerRequestDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        System.out.println("Auth request: " + authRequestDTO.toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authenticate(authRequestDTO));
    }
}
