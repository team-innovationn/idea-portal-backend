package com.ecobank.idea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "challenges")
@Getter
@Setter
public class Challenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long challenge_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "challenge")
    private List<Idea> ideas;
}
