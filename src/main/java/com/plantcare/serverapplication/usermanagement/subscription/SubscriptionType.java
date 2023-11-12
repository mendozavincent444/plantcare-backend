package com.plantcare.serverapplication.usermanagement.subscription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription_type")
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "subscription_benefits",  nullable = false, length = 512)
    private String subscriptionBenefits;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
