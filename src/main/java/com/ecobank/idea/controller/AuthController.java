package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.auth.AuthRequestDTO;
import com.ecobank.idea.dto.auth.AuthResponseDTO;
import com.ecobank.idea.dto.auth.UserRegisterRequestDTO;
//import com.ecobank.idea.entity.Department;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.VerificationToken;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.repository.VerificationTokenRepository;
import com.ecobank.idea.security.AuthService;
import com.ecobank.idea.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Tag(
        name = "Auth API",
        description = "API to register, authenticate users"
)
@RestController
@RequestMapping(API_BASE_URL + "/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenRepository tokenRepository;
    private final UserService userService;
//    private final EmailService emailService;


    @Operation(
            summary = "Register user API",
            description = "Register a new user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserRegisterRequestDTO registerRequestDTO) {
        User user = authService.register(registerRequestDTO);
//        String token = UUID.randomUUID().toString();
//        createVerificationToken(user, token);
//        emailService.sendVerificationEmail(user.getEmail(), token);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "Account created successfully!");
//        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "Check email for verification link");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @Operation(
            summary = "Update account API",
            description = "Create account for new users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status unauthorized",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(authRequestDTO));
    }

//    @Operation(
//            summary = "Fetch departments API",
//            description = "Retrieve all departments in the bank"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status OK"
//    )
//    @GetMapping("/departments")
//    public ResponseEntity<List<Department>> fetchDepartments() {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchDepartments());
//    }

    private void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(calculateExpiryDate());
        try {
            tokenRepository.save(verificationToken);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, 60);
        return new Date(calendar.getTime().getTime());
    }
}
