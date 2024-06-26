package com.ecobank.idea.service;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IdeaService {

    /**
     * @param ideaDTO
     * @return void
     * @description - Create Idea
     */
    void createIdea(IdeaDTO ideaDTO);

    /**
     * @param ideaFetchRequestDTO - Sort and Filter parameters
     * @return
     * @description - Fetch ideas based on parmeters given
     */
    Page<Idea> fetchIdeas(IdeaFetchRequestDTO ideaFetchRequestDTO);

//    Page<Idea> findUsersWithMostIdeasByStatus(IdeaEnums.Status status, IdeaFetchRequestDTO ideaFetchRequestDTO);

//    Page<Object[]> findUsersWithMostApprovedIdeas(IdeaEnums.Status status, IdeaFetchRequestDTO ideaFetchRequestDTO);
}
