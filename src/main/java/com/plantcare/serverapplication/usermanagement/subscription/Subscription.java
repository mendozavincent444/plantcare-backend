package com.plantcare.serverapplication.usermanagement.subscription;

import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionType;
import com.plantcare.serverapplication.usermanagement.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "subscription_type_id", nullable = false)
    private SubscriptionType subscriptionType;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @OneToOne(mappedBy = "subscription")
    private User user;
}
