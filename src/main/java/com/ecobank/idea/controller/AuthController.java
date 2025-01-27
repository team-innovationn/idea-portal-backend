package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.auth.AuthRequestDTO;
import com.ecobank.idea.dto.auth.AuthResponseDTO;
import com.ecobank.idea.dto.auth.OTPRequestDTO;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.security.AuthService;
import com.ecobank.idea.security.JwtUtil;
import com.ecobank.idea.service.TokenVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final AuthenticationManager authenticationManager;
    private final TokenVerificationService tokenVerificationService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "OTP Request API",
            description = "Authenticate new users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status unauthorized",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/request-otp")
    public ResponseEntity<ResponseDTO> requestOtp(@RequestBody OTPRequestDTO requestDTO) {
        System.out.println(requestDTO);
        tokenVerificationService.sendOtp(requestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Verification Code Sent Successfully!"));
    }

    @Operation(
            summary = "Authentication API",
            description = "Authenticate new users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status unauthorized",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(authRequestDTO));
    }


//    @Operation(
//            summary = "Authentication API",
//            description = "Authenticate new users"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "HTTP Status OK"
//            ),
//            @ApiResponse(
//                    responseCode = "403",
//                    description = "HTTP Status unauthorized",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDTO.class)
//                    )
//            )
//    })
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
//        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(authRequestDTO));
//    }
//    @PostMapping("/users/add")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Add multiple users to the platform")
//    // @PreAuthorize()
//    public ResponseEntity<ResponseDTO> multipleRegistration(@Valid @RequestBody List<UserRegisterRequestDTO> registerUserDTOS)
//            throws AuthenticationException {
//        return ResponseEntity.status(HttpStatus.OK).body(authService.registerMultipleUsers(registerUserDTOS));
//    }

//    @PostMapping("/verify/google")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Verify")
//    public ResponseEntity<AuthResponseDTO> verifyGoogleToken(@Valid @RequestBody TokenVerificationRequestDTO requestDTO)
//            throws AuthenticationException {
//        try {
//            String token = requestDTO.getToken();
//            System.out.println(token);
//            String userEmail = tokenVerificationService.verifyGoogleToken(token);
//            System.out.println(userEmail);
//            return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate("user@ecobank.com"));
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }

//    @PostMapping("/verify/microsoft")
//    public ResponseEntity<AuthResponseDTO> verifyMicrosoftToken(@Valid @RequestBody TokenVerificationRequestDTO requestDTO) throws AuthenticationException {
//        try {
//            String token = requestDTO.getToken();
//            System.out.println(token);
//            UserInfo userInfo = tokenVerificationService.verifyMicrosoftToken(token);
//            System.out.println(userInfo.getEmail());
//            return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate("user@ecobank.com"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }


//    private void createVerificationToken(User user, String token) {
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(token);
//        verificationToken.setUser(user);
//        verificationToken.setExpiryDate(calculateExpiryDate());
//        try {
//            tokenRepository.save(verificationToken);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }

//    private static Date calculateExpiryDate() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
//        calendar.add(Calendar.MINUTE, 60);
//        return new Date(calendar.getTime().getTime());
//    }
}
