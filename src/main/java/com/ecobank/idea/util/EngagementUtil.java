package com.ecobank.idea.util;

import com.ecobank.idea.constants.EngagementEnum;
import com.ecobank.idea.entity.Engagement;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;

public final class EngagementUtil {
    // Prevent instantiation
    private EngagementUtil() {

    }

    // Create an interaction
    public static Engagement createEngagement(User user, Idea idea, EngagementEnum engagementType) {
        Engagement engagement = new Engagement();
        engagement.setIdea(idea);
        engagement.setUser(user);
        engagement.setEngagementType(engagementType);
        return engagement;
    }

    public static int getEngagementValue(EngagementEnum engagementType) {
        return switch (engagementType) {
            case EngagementEnum.COMMENT -> 2;        // Example weight for a comment
            case EngagementEnum.LIKE -> 1;           // Example weight for a like
            case EngagementEnum.VIEW -> 3;         // Example weight for view
        };
    }
}
