package com.plantcare.serverapplication.notificationmanagement.notification;

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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "is_read_notification", nullable = false)
    private boolean isReadNotification;
}
