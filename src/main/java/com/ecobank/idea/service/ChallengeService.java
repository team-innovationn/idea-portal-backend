package com.ecobank.idea.service;

import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.dto.challenge.ChallengeFetchRequestDTO;
import com.ecobank.idea.entity.Challenge;
import org.springframework.data.domain.Page;

public interface ChallengeService {
    Page<Challenge> fetchChallenge(ChallengeFetchRequestDTO challengeFetchRequestDTO);

    // Create challenge
    void createChallenge(ChallengeDTO challengeDTO);

    // Delete challenge
    boolean deleteChallenge(String challengeId);

    // Update challenge
    Challenge updateChallenge(ChallengeDTO challengeDTO, String challengeId);
}
