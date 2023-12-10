package com.plantcare.serverapplication.notificationmanagement.notification;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {

        List<NotificationDto> notifications = this.notificationService.getAllNotifications();

        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/by-farm/{farmId}")
    public ResponseEntity<NotificationDto> addNotification(
            @RequestBody NotificationDto notificationRequest,
            @PathVariable int farmId
    ) {

        NotificationDto notificationDto = this.notificationService.addNotification(notificationRequest, farmId);

        return new ResponseEntity<>(notificationDto, HttpStatus.CREATED);
    }
}
