package com.plantcare.serverapplication.usermanagement.subscription;

import com.plantcare.serverapplication.exception.SubscriptionNotFoundException;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.SubscriptionDto;
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

        this.validateSubscription(currentUser);

        Subscription subscription = currentUser.getSubscription();
        currentUser.setSubscription(null);

        this.subscriptionRepository.delete(subscription);

        return new MessageResponseDto("Subscription cancelled successfully.");
    }

    @Override
    public SubscriptionDto getSubscription() {
        User currentUser = this.getCurrentUser();

        this.validateSubscription(currentUser);

        return this.mapToSubscriptionDto(currentUser.getSubscription());
    }

    public SubscriptionDto mapToSubscriptionDto(Subscription subscription) {
        return SubscriptionDto
                .builder()
                .id(subscription.getId())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .subscriptionType(subscription.getSubscriptionType())
                .build();
    }

    private void validateSubscription(User currentUser) {
        if (currentUser.getSubscription() == null) {
            throw new SubscriptionNotFoundException("User has no active subscriptions.");
        }
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
