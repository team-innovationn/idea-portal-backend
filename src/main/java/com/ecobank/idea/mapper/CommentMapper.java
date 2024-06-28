package com.ecobank.idea.mapper;

import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.entity.Comment;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.User;

public final class CommentMapper {
    private CommentMapper() {

    }

    public static Comment mapToComment(CommentDTO commentDTO, Comment comment, User user, Idea idea) {
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());
        comment.setIdea(idea);
        return comment;
    }

//    public static Comment mapReplyToComment(CommentReplyDTO commentReplyDTO, Comment comment, User user, Idea idea, Comment parentComment) {
//        comment.setParentComment(parentComment);
//        comment.setUser(user);
//        comment.setContent(commentReplyDTO.getContent());
//        comment.setIdea(idea);
//        return comment;
//    }
}
