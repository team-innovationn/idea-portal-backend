package com.ecobank.idea.controller;

import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.comment.CommentDTO;
import com.ecobank.idea.dto.comment.CommentFetchRequestDTO;
import com.ecobank.idea.dto.comment.CommentReplyDTO;
import com.ecobank.idea.dto.comment.CommentResponseDTO;
import com.ecobank.idea.entity.Comment;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.service.CommentService;
import com.ecobank.idea.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "Comment API",
        description = "CRUD API to CREATE, UPDATE, FETCH and DELETE comments"
)
@Slf4j
@RestController
@RequestMapping(path = API_BASE_URL + "/idea", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;
    private final SseService sseService;

    @Operation(
            summary = "Fetch Comment API",
            description = "Fetch comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Idea not found to retrieve comments"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/comments")
    public ResponseEntity<PagedResponseDTO<Comment>> getCommentsByIdeaId(
            @RequestParam(required = true) Long ideaId,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size
    ) {
        CommentFetchRequestDTO requestDTO = new CommentFetchRequestDTO();
        requestDTO.setSize(size);
        requestDTO.setPage(page);
        requestDTO.setSortDirection(sortDirection);
        requestDTO.setSortBy(sortBy);
        Page<Comment> comments = commentService.fetchComment(ideaId, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new PagedResponseDTO<>(comments));
    }

    @Operation(
            summary = "Create Comment API",
            description = "Add comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Idea not found to add comments"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        Comment newComment = commentService.createComment(commentDTO);

        // Emit event
        // Emit a new comment event using the SseService
        sseService.emitEvent("comment", newComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDTO(HttpStatus.CREATED, "Comment created successfully", newComment));
    }

    @Operation(
            summary = "Reply Comment API",
            description = "Reply comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Comment not found to reply"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/comment/reply")
    public ResponseEntity<CommentResponseDTO> replyComment(@Valid @RequestBody CommentReplyDTO commentReplyDTO) {
        Comment repliedComment = commentService.replyToComment(commentReplyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDTO(HttpStatus.CREATED, "Comment replied successfully", repliedComment));
    }

    @Operation(
            summary = "Delete Comment API",
            description = "Delete comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Comment not found to delete"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @DeleteMapping("/comment")
    public ResponseEntity<ResponseDTO> deleteComment(@RequestParam(required = true) String commentId) {
        boolean isCommentDeleted = commentService.deleteComment(commentId);
        if (!isCommentDeleted) {
            throw new ResourceNotFoundException("Comment not found!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Comment deleted successfully"));
    }

    @Operation(
            summary = "Update Comment API",
            description = "Update comments for an Idea"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS Comment not found to update"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/comment")
    public ResponseEntity<ResponseDTO> updateComment(@RequestBody CommentDTO commentDTO, @RequestParam(required = true) String commentId) {
        commentService.updateComment(commentDTO, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Comment updated successfully"));
    }
}
