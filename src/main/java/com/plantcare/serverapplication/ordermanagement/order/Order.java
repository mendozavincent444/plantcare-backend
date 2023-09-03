package com.plantcare.serverapplication.ordermanagement.order;

import com.plantcare.serverapplication.ordermanagement.address.Address;
import com.plantcare.serverapplication.usermanagement.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User orderedByUser;

    @OneToOne(optional = false)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @OneToOne(optional = false)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;
}
