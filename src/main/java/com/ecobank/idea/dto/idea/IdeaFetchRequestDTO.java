package com.ecobank.idea.dto.idea;

import com.ecobank.idea.constants.IdeaEnums;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public final class IdeaFetchRequestDTO {
    private IdeaEnums.Status status;
    private String ideaVerticalId;
    private String valueTypeId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String filter = "";      // Default filter
    private String sortBy = "";   // Default sort
    private String sortDirection = "DESC";   // Default sort
    private int page = 0;  // default page number
    private int size = 10; // default page size
}
