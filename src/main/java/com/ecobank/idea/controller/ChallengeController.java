package com.ecobank.idea.controller;

import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.dto.challenge.ChallengeFetchRequestDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.service.ChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = API_BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChallengeController {
    private final ChallengeService challengeService;

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

    @PostMapping("/challenge")
    public ResponseEntity<ResponseDTO> createChallenge(@Valid @RequestBody ChallengeDTO challengeDTO) {
        challengeService.createChallenge(challengeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Challenge created successfully"));
    }

    @DeleteMapping("/challenge")
    public ResponseEntity<ResponseDTO> deleteChallenge(@RequestParam(required = true) String challengeId) {
        boolean isChallengeDeleted = challengeService.deleteChallenge(challengeId);
        if (!isChallengeDeleted) {
            throw new ResourceNotFoundException("Challenge not found!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Challenge deleted successfully"));
    }

    @PutMapping("/challenge")
    public ResponseEntity<ResponseDTO> updateChallenge(@RequestBody ChallengeDTO challengeDTO, @RequestParam(required = true) String challengeId) {
        challengeService.updateChallenge(challengeDTO, challengeId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Challenge updated successfully"));
    }
}
