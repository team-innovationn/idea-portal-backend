package com.ecobank.idea.dto.idea;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class IdeaStatisticsDTO {
    private final long totalIdeas;
    private final long mostApprovedIdeas;
    private final long mostRejectedIdeas;
    private final long pendingIdeas;
}