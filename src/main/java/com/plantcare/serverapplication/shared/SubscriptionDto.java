package com.plantcare.serverapplication.shared;

import com.plantcare.serverapplication.usermanagement.subscriptiontype.SubscriptionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SubscriptionDto {

    private int id;
    private SubscriptionType subscriptionType;
    private Date startDate;
    private Date endDate;
}
