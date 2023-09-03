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

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

}
