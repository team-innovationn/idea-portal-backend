package com.ecobank.idea.service;

import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.entity.Interaction;
import com.ecobank.idea.entity.User;

import java.util.Optional;

public interface InteractionService {
    Optional<User> getUserWithMostInteractions();

    void saveInteraction(Interaction interaction);

    boolean userInteractionExists(long ideaId, long userId, InteractionEnum interactionEnum);
}
