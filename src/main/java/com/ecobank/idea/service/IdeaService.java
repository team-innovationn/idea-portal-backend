package com.ecobank.idea.service;

import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.idea.IdeaVertical;
import org.springframework.data.domain.Page;

import java.util.List;

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

    List<IdeaVertical> fetchIdeaVerticals();

    List<ValueType> fetchIdeaValueTypes();
}
