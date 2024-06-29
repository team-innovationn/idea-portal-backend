package com.ecobank.idea.controller;

import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.dto.challenge.ChallengeFetchRequestDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Tag(
        name = "Challenge API",
        description = "CRUD API to CREATE, UPDATE, FETCH and DELETE challenges"
)
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = API_BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChallengeController {
    private final ChallengeService challengeService;

    @Operation(
            summary = "Fetch Challenge API",
            description = "Fetch challenges for ideas"
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
    @GetMapping("/challenges")
    public ResponseEntity<PagedResponseDTO<Challenge>> fetchChallenges(@RequestParam(required = false) String filter, @RequestParam(defaultValue = "desc") String sortBy, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        ChallengeFetchRequestDTO requestDTO = new ChallengeFetchRequestDTO();
        requestDTO.setSize(size);
        requestDTO.setPage(page);
        requestDTO.setFilter(filter);
        requestDTO.setSortBy(sortBy);
        Page<Challenge> challenges = challengeService.fetchChallenge(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new PagedResponseDTO<>(challenges));
    }

    @Operation(
            summary = "Create Challenge API",
            description = "Create challenges for ideas"
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
        challengeService.createChallenge(challengeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Challenge created successfully"));
    }

    @Operation(
            summary = "Delete Challenge API",
            description = "Delete challenges for ideas"
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
    public ResponseEntity<ResponseDTO> deleteChallenge(@RequestParam(required = true) String challengeId) {
        boolean isChallengeDeleted = challengeService.deleteChallenge(challengeId);
        if (!isChallengeDeleted) {
            throw new ResourceNotFoundException("Challenge not found!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Challenge deleted successfully"));
    }

    @Operation(
            summary = "Update Challenge API",
            description = "Update challenges for ideas"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status UPDATED"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Challenge resource not found to update."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/challenge")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateChallenge(@RequestBody ChallengeDTO challengeDTO, @RequestParam(required = true) String challengeId) {
        challengeService.updateChallenge(challengeDTO, challengeId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Challenge updated successfully"));
    }
}
