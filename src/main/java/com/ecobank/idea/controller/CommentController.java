package com.ecobank.idea.controller;

import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.dto.comment.CommentReplyDTO;
import com.ecobank.idea.entity.Comment;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Slf4j
@RestController
@RequestMapping(path = API_BASE_URL + "/idea", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<PagedResponseDTO<Comment>> fetchComments(@RequestParam(required = true) String ideaId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Comment> commentPage = commentService.fetchComment(ideaId, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedResponseDTO<>(commentPage));
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment created successfully"));
    }

  @PostMapping("/comment/reply")
    public ResponseEntity<ResponseDTO> replyComment(@Valid @RequestBody CommentReplyDTO commentReplyDTO) {
        commentService.replyToComment(commentReplyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment replied successfully"));
    }

    @DeleteMapping("/comment")
    public ResponseEntity<ResponseDTO> deleteComment(@RequestParam(required = true) String commentId) {
        boolean isCommentDeleted = commentService.deleteComment(commentId);
        if (!isCommentDeleted) {
            throw new ResourceNotFoundException("Comment not found!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment deleted successfully"));
    }

    @PutMapping("/comment")
    public ResponseEntity<ResponseDTO> updateComment(@RequestBody CommentDTO commentDTO, @RequestParam(required = true) String commentId) {
        commentService.updateComment(commentDTO, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Comment updated successfully"));
    }
}
