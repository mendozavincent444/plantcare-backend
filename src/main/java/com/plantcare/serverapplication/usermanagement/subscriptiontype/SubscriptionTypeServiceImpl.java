package com.plantcare.serverapplication.usermanagement.subscriptiontype;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public List<SubscriptionType> getAllSubscriptionTypes() {
        return this.subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType getSubscriptionTypeById(int subscriptionTypeId) {

        return this.subscriptionTypeRepository.findById(subscriptionTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription Type", "id", subscriptionTypeId));
    }
}
