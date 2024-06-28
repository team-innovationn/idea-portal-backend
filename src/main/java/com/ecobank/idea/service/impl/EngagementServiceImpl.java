package com.ecobank.idea.service.impl;

import com.ecobank.idea.constants.EngagementEnum;
import com.ecobank.idea.entity.Engagement;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.repository.EngagementRepository;
import com.ecobank.idea.repository.IdeaRepository;
import com.ecobank.idea.service.EngagementService;
import com.ecobank.idea.util.EngagementUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EngagementServiceImpl implements EngagementService {
    private final IdeaRepository ideaRepository;
    private final EngagementRepository engagementRepository;

    @Override
    public Optional<Idea> getIdeaWithMostEngagements() {
        List<Idea> ideas = engagementRepository.findIdeaWithMostEngagements((Pageable) PageRequest.of(0, 1));
        return ideas.isEmpty() ? Optional.empty() : Optional.of(ideas.get(0));
    }

    @Override
    public void saveEngagement(Engagement engagement) {
        try {
            engagementRepository.save(engagement);
        } catch (Exception e) {
            throw new RuntimeException("Error saving engagement: " + e.getMessage());
        }

        Idea idea = engagement.getIdea();

        // Calculate interaction weight
        int incrementValue = EngagementUtil.getEngagementValue(engagement.getEngagementType());

        // Increment user interactions
        idea.setEngagementCount(idea.getEngagementCount() + incrementValue);

        ideaRepository.save(idea);
    }

    @Override
    public boolean userEngagementExists(long ideaId, long userId, EngagementEnum engagementEnum) {
        Optional<Engagement> engagement = engagementRepository.findEngagementByIdeaIdUserIdAndEngagementType(ideaId, userId, engagementEnum);
        return engagement.isPresent();
    }
}
