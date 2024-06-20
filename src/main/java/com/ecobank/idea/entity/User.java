package com.ecobank.idea.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 3, message = "FirstName must be at least 3 characters long")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank")
    @Size(min = 3, message = "LastName must be at least 3 characters long")
    @Column(name = "last_name")
    private String lastName;

    // Email address
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", unique = true)
    private String email;

    // Map value to Role Enum
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @NotBlank(message = "Department must not be blank")
    @Column(name = "department")
    private String department;

    @NotBlank(message = "State must not be blank")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "Password must not be blank")
    @Column(name = "password_hash")
    private String password;

    @NotBlank(message = "Branch must not be blank")
    @Column(name = "branch")
    private String branch;

    private boolean emailVerified;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Idea> ideas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getUsername() {
        return email;
    }
}
