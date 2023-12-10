package com.plantcare.serverapplication.notificationmanagement.notification;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAllNotifications();

    NotificationDto addNotification(NotificationDto notificationDto, int farmId);
}
