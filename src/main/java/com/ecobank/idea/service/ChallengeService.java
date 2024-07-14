package com.ecobank.idea.service;

import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.entity.Challenge;

public interface ChallengeService {
    // Save single challenge
    Challenge saveChallenge(ChallengeDTO challengeDTO);

    // Get single challenge
    Challenge getSingleChallenge();

    // Delete single challenge
    boolean deleteSingleChallenge();

//    Page<Challenge> fetchChallenges(ChallengeFetchRequestDTO challengeFetchRequestDTO);

    // Create challenge
    //void createChallenge(ChallengeDTO challengeDTO);


//
//    // Delete challenge
//    boolean deleteChallenge(String challengeId);
//
//    // Update challenge
//    Challenge updateChallenge(ChallengeDTO challengeDTO, String challengeId);
}
