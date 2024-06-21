package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.vote.VoteRequestDTO;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.VoteType;
import com.ecobank.idea.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@RestController
@RequestMapping(path = API_BASE_URL + "/idea/vote", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PutMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> updateVote(@RequestBody VoteRequestDTO voteRequestDTO) {
        Long ideaId = Long.valueOf(voteRequestDTO.getIdeaId().trim());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId;
        if (!(principal instanceof User)) {
            throw new RuntimeException("Unable to get ID of current logged in User");
        }
        userId = (long) ((User) principal).getUserId();
        switch (voteRequestDTO.getVoteType()) {
            case VoteType.UPVOTE -> voteService.upVoteIdea(ideaId, userId);
            case VoteType.DOWNVOTE -> voteService.downVoteIdea(ideaId, userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Vote successful"));
    }
}