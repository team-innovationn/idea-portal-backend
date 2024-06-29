package com.ecobank.idea.dto.idea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class IdeaStatisticsDTO {
    private final long totalIdeas;
    private final long mostApprovedIdeas;
    private final long mostRejectedIdeas;
    private final long pendingIdeas;
}