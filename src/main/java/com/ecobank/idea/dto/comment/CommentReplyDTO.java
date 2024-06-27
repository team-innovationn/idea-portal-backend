package com.ecobank.idea.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentReplyDTO {
    private Long commentId;

    @NotBlank(message = "Comment must not be blank")
    @Size(min = 3, message = "Comment must be at least 3 characters long")
    private String content;
}
