package com.ecobank.idea.service.impl;

import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.dto.challenge.ChallengeFetchRequestDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.mapper.ChallengeMapper;
import com.ecobank.idea.repository.ChallengeRepository;
import com.ecobank.idea.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;

    /**
     * Find challenges
     *
     * @param requestDTO
     * @return
     */
    @Override
    public Page<Challenge> fetchChallenge(ChallengeFetchRequestDTO requestDTO) {
        // Build sort object
        Sort sort = Sort.by(Sort.Direction.fromString(requestDTO.getSortBy().toLowerCase()), "createdAt");

        // Define the Pageable variable
        Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize(), sort);

        // Implemented filtering logic here, assuming `filter` is a simple keyword search in `title` and `description`.
        if (requestDTO.getFilter() != null && !requestDTO.getFilter().isEmpty()) {
            return challengeRepository.findByTitleContainingOrContentContaining(requestDTO.getFilter(), requestDTO.getFilter(), pageable);
        } else {
            return challengeRepository.findAll(pageable);
        }
    }

    @Override
    public void createChallenge(ChallengeDTO challengeDTO) {
        Challenge challenge = ChallengeMapper.mapToChallenge(challengeDTO, new Challenge());

        try {
            challengeRepository.save(challenge);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving challenge. Contact support!\n" + ex.getMessage());
        }
    }

    @Override
    public boolean deleteChallenge(String challengeId) {
        int rowsAffected;
        try {
            rowsAffected = challengeRepository.deleteChallengeById(Long.valueOf(challengeId));
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting comments");
        }
        return rowsAffected > 0;
    }

    @Override
    public Challenge updateChallenge(ChallengeDTO challengeDTO, String commentId) {
        Challenge challenge = challengeRepository.findById(Long.valueOf(commentId)).orElseThrow(() -> new ResourceNotFoundException("Challenge not found to update"));
        Challenge updatedChallenge = ChallengeMapper.mapToChallenge(challengeDTO, challenge);

        try {
            challengeRepository.save(updatedChallenge);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving comment. Contact support!");
        }
        return updatedChallenge;
    }
}
