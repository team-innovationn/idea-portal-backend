package com.ecobank.idea.dto.challenge;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChallengeFetchRequestDTO {
    private String filter = "";      // Default filter
    private String sortDirection = "ASC";   // Default sort
    private int page = 0;  // default page number
    private int size = 10; // default page size
}
