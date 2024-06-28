package com.ecobank.idea.entity;

import com.ecobank.idea.entity.idea.Idea;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    @JsonIgnore
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    // Many comments can be linked to same parent comment
    // When fetching comments, no need to serialize its parent = else it will resolve in an infinite result
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Comment parentComment;

    // When fetching comments, serialize its children
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> replies = new ArrayList<>();
}
