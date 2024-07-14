package com.ecobank.idea.service.impl;

import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.mapper.ChallengeMapper;
import com.ecobank.idea.repository.ChallengeRepository;
import com.ecobank.idea.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;

    // Create or update challenge
    @Override
    public Challenge saveChallenge(ChallengeDTO challengeDTO) {
        Challenge challenge = ChallengeMapper.mapToChallenge(challengeDTO, new Challenge());
        try {
            return challengeRepository.save(challenge);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to save challenge");
        }
    }

    // Retrieve challenge
    @Override
    public Challenge getSingleChallenge() {
        Optional<Challenge> challenge = challengeRepository.findSingleChallenge();
        return challenge.orElse(null);
    }

    // Delete challenge
    @Override
    public boolean deleteSingleChallenge() {
        try {
            challengeRepository.deleteSingleChallenge();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete challenge");
        }
    }
}
