package com.plantcare.serverapplication.usermanagement.user;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.notificationmanagement.notification.Notification;
import com.plantcare.serverapplication.ordermanagement.order.Order;
import com.plantcare.serverapplication.ordermanagement.transaction.Transaction;
import com.plantcare.serverapplication.usermanagement.role.Role;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "status", nullable = false)
    private boolean isAccountNonLocked;

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

    @OneToMany(mappedBy = "sender")
    private List<Notification> sentNotifications = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id")
    )
    private List<Notification> receivedNotifications = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_farm",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "farm_id", referencedColumnName = "id")
    )
    private List<Farm> farms = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL, mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL, mappedBy = "orderedByUser")
    private List<Order> orders = new ArrayList<>();
}
