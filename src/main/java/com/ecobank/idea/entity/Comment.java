package com.ecobank.idea.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

//    @ManyToOne
//    @JoinColumn(name = "created_by", nullable = false, insertable = false, updatable = false)
//    private User createdBy;

//    @ManyToOne
//    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
//    private User updatedBy;
}
