package com.ecobank.idea.service;

import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.dto.comment.CommentReplyDTO;
import com.ecobank.idea.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Page<Comment> fetchComment(String ideaId, int page, int size);

    List<Comment> getCommentsByIdeaId(Long ideaId);

    // Create comment
    void createComment(CommentDTO commentDTO);

    void replyToComment(CommentReplyDTO commentReplyDTO);

    // Delete comment
    boolean deleteComment(String commentId);

    // Update comment
    Comment updateComment(CommentDTO commentDTO, String commentId);
}
