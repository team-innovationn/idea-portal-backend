package com.ecobank.idea.dto.idea;

import com.ecobank.idea.constants.IdeaStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdeaDTO {
    private String challenge_id;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 3, message = "Description must be at least 3 characters long")
    private String description;

    private IdeaStatus status = IdeaStatus.PENDING;
}
