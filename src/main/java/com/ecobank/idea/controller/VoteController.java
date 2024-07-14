package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.vote.VoteRequestDTO;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.service.SseService;
import com.ecobank.idea.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;


@Tag(
        name = "Vote API",
        description = "API to vote on ideas"
)
@RestController
@RequestMapping(path = API_BASE_URL + "/idea/vote", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    private final SseService sseService;

    @Operation(
            summary = "Vote API",
            description = "Vote or un-vote an idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not found - Idea not found to vote"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> updateVote(@RequestBody VoteRequestDTO voteRequestDTO) {
        Long ideaId = Long.valueOf(voteRequestDTO.getIdeaId().trim());

        // Retrieve current logged-in user id
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId;
        if (!(principal instanceof User)) {
            throw new RuntimeException("Unable to get ID of current logged in User");
        }
        userId = ((User) principal).getUserId();

        // Vote or un-vote idea
        voteService.upVoteIdea(ideaId, userId);

//        if (isUpvote) {
//            // Emit a new comment event using the SseService
//            sseService.emitEvent("comment", comment);
//        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Vote successful"));
    }
}