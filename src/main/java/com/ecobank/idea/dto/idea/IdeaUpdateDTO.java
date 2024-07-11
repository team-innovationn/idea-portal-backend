package com.ecobank.idea.dto.idea;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdeaUpdateDTO {
    @NotBlank(message = "Status cannot be blank")
    private String status;
}
