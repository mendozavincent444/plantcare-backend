package com.plantcare.serverapplication.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
}
