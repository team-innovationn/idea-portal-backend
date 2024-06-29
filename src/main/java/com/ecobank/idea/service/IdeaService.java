package com.ecobank.idea.service;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.dto.idea.IdeaStatisticsDTO;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.idea.IdeaVertical;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IdeaService {

    void createIdea(IdeaDTO ideaDTO);

    void updateIdeaStatus(Long ideaId, IdeaEnums.Status newStatus);

    Page<Idea> fetchIdeas(IdeaFetchRequestDTO ideaFetchRequestDTO);

    List<IdeaVertical> fetchIdeaVerticals();

    List<ValueType> fetchIdeaValueTypes();

    IdeaStatisticsDTO getIdeaStatistics();
}
