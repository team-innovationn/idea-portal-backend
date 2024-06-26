package com.ecobank.idea.service;

import com.ecobank.idea.constants.EngagementEnum;
import com.ecobank.idea.entity.Engagement;
import com.ecobank.idea.entity.Idea;

import java.util.Optional;

public interface EngagementService {
    Optional<Idea> getIdeaWithMostEngagements();

    void saveEngagement(Engagement engagement);

    boolean userEngagementExists(long ideaId, long userId, EngagementEnum engagementEnum);
}
