package com.ecobank.idea.mapper;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.idea.IdeaVertical;

public final class IdeaMapper {
    private IdeaMapper() {

    }

    public static Idea mapToIdea(IdeaDTO ideaDTO, Idea idea, User user, IdeaVertical ideaVertical, ValueType valueType, Challenge challenge) {
        idea.setTitle(ideaDTO.getTitle());
        idea.setDescription(ideaDTO.getDescription());
        idea.setStatus(ideaDTO.getStatus());
        idea.setUser(user);
        idea.setCreatedBy(String.valueOf(user.getUserId()));
        idea.setChallenge(challenge);
        idea.setIdeaVertical(ideaVertical);
        idea.setValueType(valueType);
        idea.setSubmission(IdeaEnums.Submission.valueOf(ideaDTO.getSubmission().toUpperCase()));
        return idea;
    }
}
