package com.ecobank.idea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "value_types")
public class ValueType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_type_id")
    private Long valueTypeId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
