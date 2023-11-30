package com.plantcare.serverapplication.notificationmanagement.notification;

import com.plantcare.serverapplication.usermanagement.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
