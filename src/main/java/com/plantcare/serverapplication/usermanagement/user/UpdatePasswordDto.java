package com.plantcare.serverapplication.usermanagement.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {
    String currentPassword;
    String newPassword;
}
