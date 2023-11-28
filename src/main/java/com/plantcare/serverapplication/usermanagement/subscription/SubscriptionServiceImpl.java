package com.plantcare.serverapplication.usermanagement.subscription;

import com.plantcare.serverapplication.exception.SubscriptionNotFoundException;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public MessageResponseDto cancelSubscription() {
        User currentUser = this.getCurrentUser();

        if (currentUser.getSubscription() == null) {
            throw new SubscriptionNotFoundException("User has no active subscriptions.");
        }

        Subscription subscription = currentUser.getSubscription();
        currentUser.setSubscription(null);

        this.subscriptionRepository.delete(subscription);

        return new MessageResponseDto("Subscription cancelled successfully.");
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
