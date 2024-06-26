package com.ecobank.idea.service.impl;

import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.entity.Interaction;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.repository.InteractionRepository;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.service.InteractionService;
import com.ecobank.idea.util.InteractionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {

    private final UserRepository userRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public Optional<User> getUserWithMostInteractions() {
        List<User> users = interactionRepository.findUserWithMostInteractions((Pageable) PageRequest.of(0, 1));
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void saveInteraction(Interaction interaction) {
        try {
            interactionRepository.save(interaction);
        } catch (Exception e) {
            throw new RuntimeException("Error saving interactions: " + e.getMessage());
        }

        User user = interaction.getUser();

        // Calculate interaction weight
        int incrementValue = InteractionUtil.getInteractionValue(interaction.getInteractionType());

        // Increment user interactions
        user.setInteractionCount(user.getInteractionCount() + incrementValue);

        userRepository.save(user);
    }

    @Override
    public boolean userInteractionExists(long ideaId, long userId, InteractionEnum interactionEnum) {
        Optional<Interaction> interaction = interactionRepository.findInteractionByIdeaIdUserIdAndInteractionType(ideaId, userId, interactionEnum);
        return interaction.isPresent();
    }
}
