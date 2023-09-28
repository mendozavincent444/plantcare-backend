package com.plantcare.serverapplication.shared;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private int id;
    private String email;
    private boolean isAccountNonLocked;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String password;
}
