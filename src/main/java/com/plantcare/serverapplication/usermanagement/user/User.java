package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.harvestlog.HarvestLog;
import com.plantcare.serverapplication.notificationmanagement.notification.Notification;
import com.plantcare.serverapplication.ordermanagement.transaction.Transaction;
import com.plantcare.serverapplication.usermanagement.role.Role;
import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_account_non_locked", nullable = false)
    private boolean isAccountNonLocked;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "reset_token", length = 50)
    private String resetToken;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_farm",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "farm_id", referencedColumnName = "id")
    )
    private List<Farm> farms = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id")
    )
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "farmer")
    private List<HarvestLog> harvestedLog = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "is_allow_notifications", nullable = false)
    private boolean isAllowNotifications;
}
