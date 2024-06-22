package com.ecobank.idea.service;

import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> fetchComment(String ideaId, int page, int size);

    // Create comment
    void createComment(CommentDTO commentDTO);

    // Delete comment
    boolean deleteComment(String commentId);

    // Update comment
    Comment updateComment(CommentDTO commentDTO,  String commentId);
}
