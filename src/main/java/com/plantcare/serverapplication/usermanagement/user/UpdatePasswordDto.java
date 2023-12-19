package com.plantcare.serverapplication.usermanagement.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {
    @NotBlank
    String currentPassword;
    @NotBlank
    @Size(min = 5, max = 255)
    String newPassword;
    String device;
}
