package com.ecobank.idea.entity;

import com.ecobank.idea.constants.IdeaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ideas")
public class Idea extends BaseEntity {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id")
    private Long ideaId;

    @ManyToOne                                              // Many records of an idea can be mapped to a single user
    @JoinColumn(name = "user_id", nullable = false)         // Specify the foreign key relationship
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "upvotes")
    private int upvotes;

    @Column(name = "downvotes")
    private int downvotes;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING'")
    private IdeaStatus status;

    @OneToMany(mappedBy = "idea")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "idea")
    private Set<Vote> votes;
}
