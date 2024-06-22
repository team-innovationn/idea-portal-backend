package com.ecobank.idea.dto.challenge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDTO {
    @NotBlank(message = "Title must not be blank")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;


    @NotBlank(message = "Comment must not be blank")
    @Size(min = 3, message = "Comment must be at least 3 characters long")
    private String content;
}
