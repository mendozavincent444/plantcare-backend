package com.plantcare.serverapplication.schedule;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.ordermanagement.transaction.TransactionService;
import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
import com.plantcare.serverapplication.usermanagement.subscription.SubscriptionRepository;
import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionType;
import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionTypeRepository;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TransactionService transactionService;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public ScheduledTask(
            UserRepository userRepository,
            SubscriptionRepository subscriptionRepository,
            TransactionService transactionService,
            SubscriptionTypeRepository subscriptionTypeRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.transactionService = transactionService;
        this.subscriptionTypeRepository = subscriptionTypeRepository;


    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void renewMonthlySubscriptions() {

        // 1 is the id for monthly subscription
        int subscriptionType = 1;

        List<Subscription> subscriptions = this.subscriptionRepository
                .findSubscriptionByType(subscriptionType);



        if (!subscriptions.isEmpty()) {

            List<Subscription> renewedSubscriptions = this.renewSubscription(subscriptions, subscriptionType);

            this.subscriptionRepository.saveAll(renewedSubscriptions);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void renewYearlySubscriptions() {

        // 2 is the id for yearly subscription
        int subscriptionType = 2;

        List<Subscription> subscriptions = this.subscriptionRepository
                .findSubscriptionByType(subscriptionType);


        if (!subscriptions.isEmpty()) {

            List<Subscription> renewedSubscriptions = this.renewSubscription(subscriptions, subscriptionType);

            this.subscriptionRepository.saveAll(renewedSubscriptions);

        }
    }

    private List<Subscription> renewSubscription(List<Subscription> subscriptions,
                                                 int subscriptionType) {

        List<Subscription> renewedSubscriptions = new ArrayList<>();

        Date presentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(presentDate);

        if (subscriptionType == 1) {
            calendar.add(Calendar.MONTH, 1);
        } else if (subscriptionType == 2) {
            calendar.add(Calendar.YEAR, 1);
        }

        Date newEndDate = calendar.getTime();

        subscriptions.forEach(subscription -> {

            Date endDate = subscription.getEndDate();

            if (presentDate.after(endDate)) {

                subscription.setStartDate(presentDate);
                subscription.setEndDate(newEndDate);

                renewedSubscriptions.add(subscription);
            }
        });

        return renewedSubscriptions;
    }
}
