package com.plantcare.serverapplication.shared;


import lombok.*;

@Getter
@Setter
@Builder
public class UserInfoResponseDto {
    private int id;
    private String role;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
