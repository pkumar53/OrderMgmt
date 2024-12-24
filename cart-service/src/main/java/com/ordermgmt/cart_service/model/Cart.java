package com.ordermgmt.cart_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "add_date")
    private Timestamp addDate;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "discounted_price")
    private double discountedPrice;

    @Column(name = "discount_pct")
    private Double discountPercentage;

    @Column(name = "checked_out")
    private Boolean checkedOut;

    @Column(name = "checkout_date")
    private Timestamp checkOutDate;

}
