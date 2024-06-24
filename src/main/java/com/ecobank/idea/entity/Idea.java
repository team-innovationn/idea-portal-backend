package com.ecobank.idea.entity;

import com.ecobank.idea.constants.IdeaEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Title must not be blank")
    private String title;

    @Column(name = "description")
    @NotBlank(message = "Description must not be blank")
    private String description;

    @Column(name = "upvotes")
    private int upvotes;

    @Column(name = "downvotes")
    private int downvotes;

    @Column(name = "vertical")
    @NotBlank(message = "Vertical must not be blank")
    private String ideaVertical;

    @Column(name = "vertical")
    @NotBlank(message = "ValueType must not be blank")
    private String valueType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING'")
    private IdeaEnums.Status status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('GROUP','INDIVIDUAL') DEFAULT 'INDIVIDUAL'")
    private IdeaEnums.Submission submission;


    @OneToMany(mappedBy = "idea")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "idea")
    private Set<Vote> votes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
}
