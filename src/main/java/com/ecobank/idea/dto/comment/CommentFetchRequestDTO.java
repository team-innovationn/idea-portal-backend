package com.ecobank.idea.dto.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentFetchRequestDTO {
    private String sortBy = "";   // Default sort
    private String sortDirection = "DESC";   // Default
    private int page = 0;  // default page number
    private int size = 10; // // sort
}
