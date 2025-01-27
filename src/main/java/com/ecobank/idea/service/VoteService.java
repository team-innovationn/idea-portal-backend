package com.ecobank.idea.service;

public interface VoteService {

    /**
     * @param - To fetch idea to upvote
     */
    void upVoteIdea(Long ideaId, String userEmail);

    /**
     * @param ideaId
     * @param userId
     */
//    void downVoteIdea(Long ideaId, Long userId);
}
