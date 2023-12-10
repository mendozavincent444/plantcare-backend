package com.plantcare.serverapplication.notificationmanagement.notification;

import com.plantcare.serverapplication.shared.MessageResponseDto;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAllNotifications();

    NotificationDto addNotification(NotificationDto notificationDto, int farmId);

    MessageResponseDto toggleIsReadNotifications(int notificationId);
}
