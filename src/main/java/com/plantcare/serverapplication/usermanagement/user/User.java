package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.notificationmanagement.notification.Notification;
import com.plantcare.serverapplication.usermanagement.role.Role;
import jakarta.mail.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PrivateKey;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email", nullable = false, unique = true
    )
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // fix ERD
    @OneToMany(mappedBy = "sender")
    private List<Notification> sentNotifications;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id")
    )
    private List<Notification> receivedNotifications;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_farm",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "farm_id", referencedColumnName = "id")
    )
    private List<Farm> farms;
}
