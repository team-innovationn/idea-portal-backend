package com.ecobank.idea.entity;

import com.ecobank.idea.entity.idea.Idea;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Title must not be blank")
    private String title;

    @Column(name = "content")
    @NotBlank(message = "Content must not be blank")
    private String content;

    @OneToMany(mappedBy = "challenge")
    private List<Idea> ideas;
}
