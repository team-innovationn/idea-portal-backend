package com.ecobank.idea.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "challenges")
public class Challenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    @Column(name = "title")
    private String title;

    @Column(name = "challenge")
    private String challenge;
}
