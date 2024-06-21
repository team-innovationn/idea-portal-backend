package com.ecobank.idea.dto.idea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IdeaFetchRequestDTO {
    private String filter = "";      // Default filter
    private String sortBy = "ASC";   // Default sort
    private int page = 0;  // default page number
    private int size = 10; // default page size
}
