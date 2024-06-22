package com.ecobank.idea.mapper;

import com.ecobank.idea.dto.challenge.ChallengeDTO;
import com.ecobank.idea.entity.Challenge;

public final class ChallengeMapper {
    private ChallengeMapper() {

    }

    public static Challenge mapToChallenge(ChallengeDTO challengeDTO, Challenge challenge) {
        challenge.setTitle(challengeDTO.getTitle());
        challenge.setContent(challengeDTO.getContent());
        return challenge;
    }

}
