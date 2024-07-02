package com.ecobank.idea.service;

import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.dto.comment.CommentFetchRequestDTO;
import com.ecobank.idea.dto.comment.CommentReplyDTO;
import com.ecobank.idea.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Page<Comment> fetchComment(Long ideaId, CommentFetchRequestDTO requestDTO);

    List<Comment> getCommentsByIdeaId(Long ideaId);

    // Create comment
    Comment createComment(CommentDTO commentDTO);

    Comment replyToComment(CommentReplyDTO commentReplyDTO);

    // Delete comment
    boolean deleteComment(String commentId);

    // Update comment
    Comment updateComment(CommentDTO commentDTO, String commentId);
}
