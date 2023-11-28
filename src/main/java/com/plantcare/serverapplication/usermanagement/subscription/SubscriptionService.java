package com.plantcare.serverapplication.usermanagement.subscription;

import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.SubscriptionDto;

public interface SubscriptionService {

    public MessageResponseDto cancelSubscription();

    public SubscriptionDto getSubscription();

    public SubscriptionDto mapToSubscriptionDto(Subscription subscription);
}
