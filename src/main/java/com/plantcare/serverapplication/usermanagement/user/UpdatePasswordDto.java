package com.plantcare.serverapplication.usermanagement.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {
    @NotBlank
    String currentPassword;
    @NotBlank
    String newPassword;
    @NotBlank
    String device;
}
