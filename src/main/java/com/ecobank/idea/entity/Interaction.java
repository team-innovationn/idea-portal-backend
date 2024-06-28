package com.ecobank.idea.entity;

import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.entity.idea.Idea;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "interactions")
@EntityListeners(AuditingEntityListener.class)                      // Listen for changes made
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long interactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('LIKE','COMMENT', 'CREATE')")
    private InteractionEnum interactionType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
