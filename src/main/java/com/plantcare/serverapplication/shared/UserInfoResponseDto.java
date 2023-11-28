package com.plantcare.serverapplication.shared;


import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
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
    private boolean allowNotifications;
    private SubscriptionDto subscription;
}
