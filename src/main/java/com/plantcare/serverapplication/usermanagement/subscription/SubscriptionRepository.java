package com.plantcare.serverapplication.usermanagement.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query(value = "SELECT * FROM subscription s WHERE " +
            "s.subscription_type_id = :type", nativeQuery = true)
    List<Subscription> findSubscriptionByType(@Param("type") int type);
}
