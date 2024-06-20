package com.ecobank.idea.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdeaFetchRequestDTO {
    private String filter;
    private String sortBy;
    private int page = 0;  // default page number
    private int size = 10; // default page size
}
