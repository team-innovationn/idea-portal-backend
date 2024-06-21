package com.ecobank.idea.service.impl;

import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.Vote;
import com.ecobank.idea.entity.VoteType;
import com.ecobank.idea.exception.DuplicateRequestException;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.repository.IdeaRepository;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.repository.VoteRepository;
import com.ecobank.idea.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final UserRepository userRepository;
    private final IdeaRepository ideaRepository;
    private final VoteRepository voteRepository;

    /**
     * @param
     */
    @Override
    @Transactional
    public void upVoteIdea(Long ideaId, Long userId) {
        Vote vote;
        boolean voteExists = false;

        // Check if a vote entry exists
        Optional<Vote> userVote = voteRepository.findByUserIdAndIdeaId(userId, ideaId);

        // If no vote found, create one with an upvote
        if (userVote.isEmpty()) {
            vote = new Vote();
        } else if (userVote.get().getVoteType() == VoteType.UPVOTE) {
            // If a vote exists and there it's an upvote.
            throw new DuplicateRequestException("User has already voted on this idea");
        } else {
            voteExists = true;
            vote = userVote.get();
        }

        // Retrieve idea and votes
        User user = getUserById(userId);
        Idea idea = getIdeaById(ideaId);

        // Map upvote
        mapToVote(user, idea, vote, VoteType.UPVOTE);

        try {
            voteRepository.save(vote);
        } catch (Exception exception) {
            throw new RuntimeException("Unable to vote: " + exception.getMessage());
        }

        // Increase upvote on idea
        try {
            idea.setUpvotes(idea.getUpvotes() + 1);

            // Decrease the downvote, if vote has been registered as one
            if (voteExists) {
                idea.setDownvotes(idea.getDownvotes() - 1);
            }
            ideaRepository.save(idea);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Unable to save vote counts");
        }
    }

    /**
     * @param
     */
    @Override
    @Transactional
    public void downVoteIdea(Long ideaId, Long userId) {
        Vote vote;
        boolean voteExists = false;

        // Check if a vote entry exists
        Optional<Vote> userVote = voteRepository.findByUserIdAndIdeaId(userId, ideaId);

        // If no vote found, create one with an upvote
        if (userVote.isEmpty()) {
            vote = new Vote();
        } else if (userVote.get().getVoteType() == VoteType.DOWNVOTE) {
            // If a vote exists and there it's an upvote.
            throw new DuplicateRequestException("User has already down voted on this idea");
        } else {
            voteExists = true;
            vote = userVote.get();
        }

        // Retrieve idea and votes
        User user = getUserById(userId);
        Idea idea = getIdeaById(ideaId);

        // Map upvote
        mapToVote(user, idea, vote, VoteType.DOWNVOTE);

        try {
            voteRepository.save(vote);
        } catch (Exception exception) {
            throw new RuntimeException("Unable to vote");
        }

        // Increase downvote on idea
        try {
            idea.setDownvotes(idea.getDownvotes() + 1);

            // Decrease the upvote, if vote has been registered as one
            if (voteExists) {
                idea.setUpvotes(idea.getUpvotes() - 1);
            }
            ideaRepository.save(idea);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Unable to save vote counts");
        }
    }


    // Fetch user by userId
    private User getUserById(Long userId) {
        System.out.println(userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found to vote on idea"));
        return user;
    }

    // Fetch idea by ideaId
    private Idea getIdeaById(Long userId) {
        Idea idea = ideaRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Idea to vote not found"));
        return idea;
    }

    private Vote mapToVote(User user, Idea idea, Vote vote, VoteType voteType) {
        vote.setUser(user);
        vote.setIdea(idea);
        vote.setVoteType(voteType);
        return vote;
    }
}
