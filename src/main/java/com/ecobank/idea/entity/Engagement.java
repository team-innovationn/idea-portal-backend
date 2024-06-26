package com.ecobank.idea.entity;

import com.ecobank.idea.constants.EngagementEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "engagements")
@EntityListeners(AuditingEntityListener.class)                      // Listen for changes made
public class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engagement_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @Enumerated(EnumType.STRING)
    @Column(name = "engagement_type", columnDefinition = "enum('LIKE','COMMENT', 'CREATE')")
    private EngagementEnum engagementType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
