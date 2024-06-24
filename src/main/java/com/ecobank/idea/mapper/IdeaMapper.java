package com.ecobank.idea.mapper;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;

public final class IdeaMapper {
    private IdeaMapper() {

    }

    public static Idea mapToIdea(IdeaDTO ideaDTO, Idea idea, User user, Challenge challenge) {
        idea.setTitle(ideaDTO.getTitle());
        idea.setDescription(ideaDTO.getDescription());
        idea.setStatus(ideaDTO.getStatus());
        idea.setUser(user);
        idea.setCreatedBy(String.valueOf(user.getUserId()));
        idea.setChallenge(challenge);
        idea.setIdeaVertical(ideaDTO.getIdeaVertical());
        idea.setValueType(ideaDTO.getValueType());
        idea.setSubmission(IdeaEnums.Submission.valueOf(ideaDTO.getSubmission().toUpperCase()));
        return idea;
    }
}
