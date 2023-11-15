package com.plantcare.serverapplication.auth;

import lombok.Getter;

@Getter
public class ForgotPasswordRequestDto {
    private String email;
    private String newPassword;
}
