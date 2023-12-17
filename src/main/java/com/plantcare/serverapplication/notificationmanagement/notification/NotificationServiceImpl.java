package com.plantcare.serverapplication.notificationmanagement.notification;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import com.plantcare.serverapplication.usermanagement.user.UserService;
import org.springframework.stereotype.Service;

import javax.management.NotificationFilter;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            UserService userService,
            FarmRepository farmRepository,
            UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        User currentUser = this.userService.getCurrentUser();

        List<Notification> notifications = currentUser.getNotifications();

        return notifications.stream().map(notification -> this.mapToDto(notification)).toList();
    }

    @Override
    public NotificationDto addNotification(NotificationDto notificationDto, int farmId) {

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("id", "Farm", farmId));

        List<User> users = farm.getUsers();

        Notification notification = Notification.builder()
                .date(new Date())
                .content(notificationDto.getContent())
                .title(notificationDto.getTitle())
                .isReadNotification(false)
                .build();

        Notification savedNotification = this.notificationRepository.save(notification);

        users.forEach(user -> user.getNotifications().add(savedNotification));

        this.userRepository.saveAll(users);

        return this.mapToDto(savedNotification);
    }

    @Override
    public MessageResponseDto toggleIsReadNotifications(ToggleNotificationDto toggleNotificationDto, int notificationId) {

        Notification notification = this.notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("id", "Notification", notificationId));

        boolean isReadNotification = toggleNotificationDto.isReadNotification();

        notification.setReadNotification(isReadNotification);

        this.notificationRepository.save(notification);

        return new MessageResponseDto("Notification read.");
    }

    private NotificationDto mapToDto(Notification notification) {
        return NotificationDto
                .builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .date(notification.getDate())
                .content(notification.getContent())
                .isReadNotification(notification.isReadNotification())
                .build();

    }
}
