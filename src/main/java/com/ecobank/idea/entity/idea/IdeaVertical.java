package com.ecobank.idea.entity.idea;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "idea_verticals")
public class IdeaVertical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vertical_id")
    private Long verticalId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}