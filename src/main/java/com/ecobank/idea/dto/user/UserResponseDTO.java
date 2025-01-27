package com.ecobank.idea.dto.user;

import com.ecobank.idea.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private int userId;
    private String firstName;
    private String email;
    private String department;
    private String lastName;
    private String state;
    private String branch;
    private int interactions;
    private Role role;
    private boolean profileUpdated;
}