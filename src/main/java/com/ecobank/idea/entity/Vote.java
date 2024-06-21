package com.ecobank.idea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "votes")
@Getter
@Setter
public class Vote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('UPVOTE','DOWNVOTE') NOT NULL")
    private VoteType voteType;
}
