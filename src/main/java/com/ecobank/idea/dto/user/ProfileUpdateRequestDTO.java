package com.ecobank.idea.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileUpdateRequestDTO {
    private String firstName;
    private String lastName;
    private String department;
    private String state;
    private String branch;
}