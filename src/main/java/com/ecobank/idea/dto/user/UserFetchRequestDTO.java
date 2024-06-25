package com.ecobank.idea.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserFetchRequestDTO {
    private String filter = "";      // Default filter
    private String sortBy = "";   // Default sort
    private String sortDirection = "DESC";   // Default sort
    private int page = 0;  // default page number
    private int size = 10; // default page size
}

