package com.plantcare.serverapplication.notificationmanagement.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class NotificationDto {
    private int id;
    private Date date;
    private String content;
    private String title;
    private boolean isReadNotification;
}
