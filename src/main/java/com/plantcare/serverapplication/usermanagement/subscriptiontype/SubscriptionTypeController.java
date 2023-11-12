package com.plantcare.serverapplication.usermanagement.subscriptiontype;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/subscription-types")
public class SubscriptionTypeController {
    private final SubscriptionTypeService subscriptionTypeService;

    public SubscriptionTypeController(SubscriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionType>> getAllSubscriptionTypes() {

        List<SubscriptionType> subscriptionTypes = this.subscriptionTypeService.getAllSubscriptionTypes();

        return ResponseEntity.ok(subscriptionTypes);
    }

    @GetMapping("/{subscriptionTypeId}")
    public ResponseEntity<SubscriptionType> getSubscriptionTypeById(@PathVariable int subscriptionTypeId) {

        return ResponseEntity.ok(this.subscriptionTypeService.getSubscriptionTypeById(subscriptionTypeId));
    }
}
