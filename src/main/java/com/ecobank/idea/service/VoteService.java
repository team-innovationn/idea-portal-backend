package com.ecobank.idea.service;

public interface VoteService {

    /**
     * @param - To fetch idea to upvote
     */
    void upVoteIdea(Long ideaId, Long userId);

    /**
     * @param ideaId
     * @param userId
     */
    void downVoteIdea(Long ideaId, Long userId);
}
