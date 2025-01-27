package com.ecobank.idea.dto.auth;

import com.ecobank.idea.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;

    private UserResponseDTO user;

    private int exp;
}

