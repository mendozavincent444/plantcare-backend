package com.plantcare.serverapplication.usermanagement.subscription;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @DeleteMapping
    public ResponseEntity<MessageResponseDto> cancelSubscription() {

        MessageResponseDto messageResponseDto = this.subscriptionService.cancelSubscription();

        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

}
