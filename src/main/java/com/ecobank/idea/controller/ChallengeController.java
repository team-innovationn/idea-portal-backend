package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Tag(
        name = "Challenge API",
        description = "CRUD API to CREATE, UPDATE, FETCH and DELETE challenge"
)
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = API_BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChallengeController {
    private final ChallengeService challengeService;

    @Operation(
            summary = "Fetch Challenge API",
            description = "Fetch challenge for ideas"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/challenge")
    public ResponseEntity<Challenge> fetchChallenge() {
        Challenge challenge = challengeService.getSingleChallenge();
        if (null == challenge) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(challenge);
    }

    @Operation(
            summary = "Create or Update Challenge API",
            description = "Create or Update challenges for idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/challenge")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> createChallenge(@Valid @RequestBody ChallengeDTO challengeDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());
        challengeService.saveChallenge(challengeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Challenge created successfully"));
    }

    @Operation(
            summary = "Delete Challenge API",
            description = "Delete challenge for ideas"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status DELETED"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Challenge resource not found."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @DeleteMapping("/challenge")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteChallenge() {
        boolean isChallengeDeleted = challengeService.deleteSingleChallenge();
        if (!isChallengeDeleted) {
            throw new ResourceNotFoundException("Challenge not found!");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED, "Challenge deleted successfully"));
    }
}
