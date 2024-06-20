package com.ecobank.idea.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('UPVOTE','DOWNVOTE') NOT NULL")
    private VoteType voteType;

//    @ManyToOne
//    @JoinColumn(name = "created_by", nullable = false, insertable = false, updatable = false)
//    private User createdBy;
//
//
//    @ManyToOne
//    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
//    private User updatedBy;
}
