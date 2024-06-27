package com.ecobank.idea.entity;

import com.ecobank.idea.constants.IdeaEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "vertical")
    private String ideaVertical;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "engagement_column")
    @JsonProperty("engagements")
    private int engagementCount = 0;

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
