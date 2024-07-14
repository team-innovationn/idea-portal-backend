package com.ecobank.idea.service.impl;

import com.ecobank.idea.constants.EngagementEnum;
import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.entity.Engagement;
import com.ecobank.idea.entity.Interaction;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.Vote;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.repository.IdeaRepository;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.repository.VoteRepository;
import com.ecobank.idea.service.EngagementService;
import com.ecobank.idea.service.InteractionService;
import com.ecobank.idea.service.VoteService;
import com.ecobank.idea.util.EngagementUtil;
import com.ecobank.idea.util.InteractionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final InteractionService interactionService;
    private final EngagementService engagementService;
    private final UserRepository userRepository;
    private final IdeaRepository ideaRepository;
    private final VoteRepository voteRepository;

    @Override
    @Transactional
    public void upVoteIdea(Long ideaId, Long userId) {
        // Fetch Idea
        Idea idea = getIdeaById(ideaId);

        // Check if a vote entry exists
        Optional<Vote> userVote = voteRepository.findByUserIdAndIdeaId(userId, ideaId);

        // If vote exists? delete it && decrease idea upvotes
        if (userVote.isPresent()) {
            try {
                voteRepository.deleteByUserIdAndIdeaId(userId, ideaId);
                idea.setUpvotes(idea.getUpvotes() - 1);
                ideaRepository.save(idea);
                return;
            } catch (Exception e) {
                throw new RuntimeException("Unable to delete vote: " + e.getMessage());
            }
        }
        // Create new vote
        Vote vote = new Vote();

        // Get user required for a vote entry
        User user = getUserById(userId);

        // Create a vote entry
        mapToVote(user, idea, vote);

        // Save vote
        try {
            voteRepository.save(vote);
            idea.setUpvotes(idea.getUpvotes() + 1);
            ideaRepository.save(idea);
        } catch (Exception exception) {
            throw new RuntimeException("Unable to vote: " + exception.getMessage());
        }

        // If no interaction is found, create one
        if (!interactionService.userInteractionExists(ideaId, userId, InteractionEnum.LIKE)) {
            // Create an interaction
            Interaction interaction = InteractionUtil.createInteraction(user, idea, InteractionEnum.LIKE);

            // Save interaction
            interactionService.saveInteraction(interaction);
        }

        if (!engagementService.userEngagementExists(ideaId, userId, EngagementEnum.LIKE)) {
            // Create engagement
            Engagement engagement = EngagementUtil.createEngagement(user, idea, EngagementEnum.LIKE);

            // Save engagement
            engagementService.saveEngagement(engagement);
        }
    }

    // Fetch user by userId
    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found to vote on idea"));
    }

    // Fetch idea by ideaId
    private Idea getIdeaById(Long userId) {
        return ideaRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Idea to vote not found"));
    }

    // Map to vote entity
    private void mapToVote(User user, Idea idea, Vote vote) {
        vote.setUser(user);
        vote.setIdea(idea);
    }
}
