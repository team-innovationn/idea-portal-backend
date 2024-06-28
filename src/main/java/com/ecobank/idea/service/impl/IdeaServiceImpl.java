package com.ecobank.idea.service.impl;

import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.Interaction;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.idea.IdeaVertical;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.mapper.IdeaMapper;
import com.ecobank.idea.repository.*;
import com.ecobank.idea.security.SecurityUtil;
import com.ecobank.idea.service.IdeaService;
import com.ecobank.idea.service.InteractionService;
import com.ecobank.idea.util.InteractionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {
    private final UserRepository userRepository;
    private final IdeaRepository ideaRepository;
    private final ChallengeRepository challengeRepository;
    private final IdeaVerticalRepository ideaVerticalRepository;
    private final ValueTypeRepository valueTypeRepository;
    private final InteractionService interactionService;

    @Override
    public void createIdea(IdeaDTO ideaDTO) {
        // Get currently logged-in user;
        String username = SecurityUtil.getCurrentUsername();

        // Fetch the user entity using the username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("An internal error has occurred! User not found. Contact support"));

        // Fetch vertical
        IdeaVertical ideaVertical = ideaVerticalRepository.findById(Long.valueOf(ideaDTO.getIdeaVerticalId())).orElseThrow(() -> new ResourceNotFoundException("Idea Vertical selected not valid"));

        // Fetch vertical
        ValueType valueType = valueTypeRepository.findById(Long.valueOf(ideaDTO.getValueTypeId())).orElseThrow(() -> new ResourceNotFoundException("Value type selected not valid"));

        System.out.println(ideaVertical);
        System.out.println(valueType);

        // Retrieve challenge associated with idea if any
        Challenge challenge = null;
        if (null != ideaDTO.getChallenge_id() && !ideaDTO.getChallenge_id().isEmpty()) {
            Optional<Challenge> fetchChallenge = challengeRepository.findById(Long.valueOf(ideaDTO.getChallenge_id()));
            if (fetchChallenge.isPresent()) {
                challenge = fetchChallenge.get();
            }
        }
        // Create Idea entity instance from user input
        Idea idea = IdeaMapper.mapToIdea(ideaDTO, new Idea(), user, ideaVertical, valueType, challenge);

        try {
            ideaRepository.save(idea);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving idea " + ex.getMessage() + ". Contact support!");
        }

        // Increment user Idea count'
        user.setIdeaCount(user.getIdeaCount() + 1);

        // Persist user
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving user idea count: " + ex.getMessage());
        }

        // Create an interaction
        Interaction interaction = InteractionUtil.createInteraction(user, idea, InteractionEnum.CREATE);

        // Save interaction
        interactionService.saveInteraction(interaction);
    }

    @Override
    public Page<Idea> fetchIdeas(IdeaFetchRequestDTO requestDTO) {
        // Build sort object
        Sort sort = Sort.by(Sort.Direction.fromString(requestDTO.getSortDirection().toLowerCase()), requestDTO.getSortBy());

        // Define the Pageable variable
        Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize(), sort);

        // Implemented filtering logic here, assuming `filter` is a simple keyword search in `title` and `description`.
        if (requestDTO.getFilter() != null && !requestDTO.getFilter().isEmpty()) {
            return ideaRepository.findByTitleContainingOrDescriptionContaining(requestDTO.getFilter(), requestDTO.getFilter(), pageable);
        } else {
            return ideaRepository.findAll(pageable);
        }
    }

    // Fetch all idea verticals
    @Override
    public List<IdeaVertical> fetchIdeaVerticals() {
        return ideaVerticalRepository.findAll();
    }

    // Fetch all idea value types
    @Override
    public List<ValueType> fetchIdeaValueTypes() {
        return valueTypeRepository.findAll();
    }
}
