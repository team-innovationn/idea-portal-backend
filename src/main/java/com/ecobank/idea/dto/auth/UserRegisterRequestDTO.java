package com.ecobank.idea.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 3, message = "FirstName must be at least 3 characters long")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank")
    @Size(min = 3, message = "LastName must be at least 3 characters long")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Department must not be blank")
    private String departmentId;

    @NotBlank(message = "State must not be blank")
    private String state;

    @NotBlank(message = "Branch must not be blank")
    private String branch;
}
