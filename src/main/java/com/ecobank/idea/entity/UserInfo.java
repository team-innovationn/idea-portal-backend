package com.ecobank.idea.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String email;
    private String name;
    private String picture;
}
