package com.ecobank.idea.dto.comment;

import com.ecobank.idea.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CommentResponseDTO {
    @Schema(
            description = "Status code of response"
    )
    private HttpStatus statusCode;

    @Schema(
            description = "Message "
    )
    private String statusMsg;

    @Schema(
            description = "Payload"
    )
    private Comment payload;
}
