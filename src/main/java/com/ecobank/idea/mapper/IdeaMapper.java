package com.ecobank.idea.mapper;

import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;

public final class IdeaMapper {
    private IdeaMapper() {

    }

    public static Idea mapToIdea(IdeaDTO ideaDTO, Idea idea, User user) {
        idea.setTitle(ideaDTO.getTitle());
        idea.setDescription(ideaDTO.getDescription());
        idea.setStatus(ideaDTO.getStatus());
        idea.setUser(user);
        idea.setCreatedBy(String.valueOf(user.getUserId()));
        return idea;
    }
}
