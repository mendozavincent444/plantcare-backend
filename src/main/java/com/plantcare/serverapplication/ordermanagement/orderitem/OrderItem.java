package com.plantcare.serverapplication.ordermanagement.orderitem;

import com.plantcare.serverapplication.ordermanagement.order.Order;
import com.plantcare.serverapplication.ordermanagement.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    // fix, create bidirectional one to many between order and orderitem
    /*
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
     */

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
