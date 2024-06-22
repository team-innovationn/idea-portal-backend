package com.ecobank.idea.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDTO {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String department;
        private String state;
        private String branch;
}
