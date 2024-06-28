package com.ecobank.idea.service.impl;

import com.ecobank.idea.constants.EngagementEnum;
import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.dto.comment.CommentReplyDTO;
import com.ecobank.idea.entity.*;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.mapper.CommentMapper;
import com.ecobank.idea.repository.CommentRepository;
import com.ecobank.idea.repository.IdeaRepository;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.security.SecurityUtil;
import com.ecobank.idea.service.CommentService;
import com.ecobank.idea.service.EngagementService;
import com.ecobank.idea.service.InteractionService;
import com.ecobank.idea.util.EngagementUtil;
import com.ecobank.idea.util.InteractionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final InteractionService interactionService;
    private final EngagementService engagementService;

    @Override
    public Page<Comment> fetchComment(String ideaId, int page, int size) {
        // Build sort object
        Sort sort = Sort.by(Sort.Direction.fromString("desc"), "createdAt");

        Pageable pageable = PageRequest.of(page, size, sort);
        return commentRepository.findByIdea_IdeaId(Long.valueOf(ideaId), pageable);
    }

    @Override
    public void createComment(CommentDTO commentDTO) {
        // Get currently logged-in user;
        String username = SecurityUtil.getCurrentUsername();

        // Fetch the user entity using the username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("An internal error has occurred! User not found. Contact support"));
        Idea ideaToComment = ideaRepository.findById(Long.valueOf(commentDTO.getIdeaId())).orElseThrow(() -> new ResourceNotFoundException("Idea not found. Ensure the right idea Id is passed. Also contact support"));

        // Build up comment
        Comment comment = CommentMapper.mapToComment(commentDTO, new Comment(), user, ideaToComment);

        // Save comment
        try {
            commentRepository.save(comment);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving comment. Contact support!");
        }

        // Create an interaction
        Interaction interaction = InteractionUtil.createInteraction(user, ideaToComment, InteractionEnum.COMMENT);

        // Save interaction
        interactionService.saveInteraction(interaction);


        // Create an idea engagement
        Engagement engagement = EngagementUtil.createEngagement(user, ideaToComment, EngagementEnum.COMMENT);

        // Save idea engagement
        engagementService.saveEngagement(engagement);
    }

    // Reply to comment
    @Override
    public void replyToComment(CommentReplyDTO commentReplyDTO) {
        Comment parentComment = commentRepository.findById(commentReplyDTO.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment to reply does not exist"));
        Idea idea = parentComment.getIdea();
        User user = parentComment.getUser();

        // Build up comment
        Comment childComment = new Comment();
        childComment.setUser(user);
        childComment.setIdea(idea);
        childComment.setContent(commentReplyDTO.getContent());
        childComment.setParentComment(parentComment);


        // Save comment
        try {
            // Save child comment
            commentRepository.save(childComment);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving reply." + ex.getMessage() + " Contact support!");
        }

        // Create an interaction
        Interaction interaction = InteractionUtil.createInteraction(user, idea, InteractionEnum.COMMENT);

        // Save interaction
        interactionService.saveInteraction(interaction);

        // Create an idea engagement
        Engagement engagement = EngagementUtil.createEngagement(user, idea, EngagementEnum.COMMENT);

        // Save idea engagement
        engagementService.saveEngagement(engagement);
    }

    @Override
    public boolean deleteComment(String commentId) {
        int rowsAffected;
        try {
            rowsAffected = commentRepository.deleteCommentById(Long.valueOf(commentId));
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting comments");
        }
        return rowsAffected > 0;
    }

    @Override
    public Comment updateComment(CommentDTO commentDTO, String commentId) {
        Comment comment = commentRepository.findById(Long.valueOf(commentId)).orElseThrow(() -> new ResourceNotFoundException("Comment not found to update"));
        Comment updatedComment = CommentMapper.mapToComment(commentDTO, comment, comment.getUser(), comment.getIdea());
        try {
            commentRepository.save(updatedComment);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Error saving comment. Contact support!");
        }
        return updatedComment;
    }


    // Fetch user by userId
    private User getUserByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found to vote on idea"));
    }

    // Fetch idea by ideaId
    private Idea getIdeaById(Long userId) {
        return ideaRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Idea to vote not found"));
    }
}
