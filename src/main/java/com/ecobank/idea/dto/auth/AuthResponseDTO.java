package com.ecobank.idea.dto.auth;

import com.ecobank.idea.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private User user;
    private int exp;
}
