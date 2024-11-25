package com.ordermgmt.order_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_details")
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "order_completed_date", nullable = false)
    private LocalDateTime orderCompletedDate;
}
