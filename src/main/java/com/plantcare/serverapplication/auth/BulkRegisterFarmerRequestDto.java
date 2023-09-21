package com.plantcare.serverapplication.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BulkRegisterFarmerRequestDto {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
