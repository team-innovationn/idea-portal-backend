package com.ecobank.idea.entity;

import com.ecobank.idea.entity.idea.Idea;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // Email address
    @Column(name = "email", unique = true)
    private String email;

    // Foreign relationship with department - for integrity
//    @ManyToOne
//    @JoinColumn(name = "department_id", nullable = false)
//    private Department department;
    private String department;

    // Map value to Role Enum
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "state")
    private String state;

    @Column(name = "branch")
    private String branch;

    @JsonIgnore
    @Column(name = "password_hash")
    private String password;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "interaction_count")
    @JsonProperty("interactions")
    private int interactionCount = 0;

    @Column(name = "idea_count")
    private int ideaCount = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Idea> ideas;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }
}
