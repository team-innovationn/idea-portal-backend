package com.ecobank.idea.service.impl;

import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.entity.Challenge;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.mapper.IdeaMapper;
import com.ecobank.idea.repository.ChallengeRepository;
import com.ecobank.idea.repository.IdeaRepository;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.security.SecurityUtil;
import com.ecobank.idea.service.IdeaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {
    private final UserRepository userRepository;
    private final IdeaRepository ideaRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public void createIdea(IdeaDTO ideaDTO) {
        // Get currently logged-in user;
        String username = SecurityUtil.getCurrentUsername();

        // Fetch the user entity using the username
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("An internal error has occurred! User not found. Contact support")
        );

        Challenge challenge = null;
        if (null != ideaDTO.getChallenge_id() && !ideaDTO.getChallenge_id().isEmpty()) {
            Optional<Challenge> fetchChallenge = challengeRepository.findById(Long.valueOf(ideaDTO.getChallenge_id()));
            if (fetchChallenge.isPresent()) {
                challenge = fetchChallenge.get();
            }
        }

        // Get Idea
        Idea idea = IdeaMapper.mapToIdea(ideaDTO, new Idea(), user, challenge);
        try {
            ideaRepository.save(idea);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving idea. Contact support!");
        }
    }

    @Override
    public Page<Idea> fetchIdeas(IdeaFetchRequestDTO request) {
        // Build sort object
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortBy().toLowerCase()), "createdAt");

        // Define the Pageable variable
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        // Implemented filtering logic here, assuming `filter` is a simple keyword search in `title` and `description`.
        if (request.getFilter() != null && !request.getFilter().isEmpty()) {
            return ideaRepository.findByTitleContainingOrDescriptionContaining(request.getFilter(), request.getFilter(), pageable);
        } else {
            return ideaRepository.findAll(pageable);
        }
    }

}
