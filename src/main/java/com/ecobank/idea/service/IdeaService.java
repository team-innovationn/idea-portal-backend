package com.ecobank.idea.service;

import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.entity.Idea;
import org.springframework.data.domain.Page;

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


}
