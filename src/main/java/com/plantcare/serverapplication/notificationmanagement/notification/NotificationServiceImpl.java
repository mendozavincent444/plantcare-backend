package com.plantcare.serverapplication.notificationmanagement.notification;

import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        User currentUser = this.userService.getCurrentUser();

        List<Notification> notifications = currentUser.getNotifications();

        return notifications.stream().map(notification -> this.mapToDto(notification)).toList();
    }

    private NotificationDto mapToDto(Notification notification) {
        return NotificationDto
                .builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .date(notification.getDate())
                .content(notification.getContent())
                .build();

    }
}
