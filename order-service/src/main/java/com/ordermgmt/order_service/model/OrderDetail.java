package com.ordermgmt.order_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "discount_per_quantity", nullable = false)
    private Double discountPerQuantity;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "discount_pct", nullable = false)
    private Double discountPercentage;

    @Column(name = "price_per_quantity", nullable = false)
    private Double pricePerQuantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discounted_price_per_quantity", nullable = false)
    private Double discountedPricePerQuantity;

    @Column(name = "discounted_price", nullable = false)
    private Double discountedPrice;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "delivery_date")
    private Double deliveryDate;

    @Column(name = "refund_date")
    private Double refundDate;

    @Column(name = "shipping_date")
    private Double shippingDate;

    @Column(name = "return_date")
    private Double returnDate;

    @Column(name = "exchange_date")
    private Double exchangeDate;

    @Column(name = "payment_mode")
    private Double paymentMode;

    @Column(name = "sender_payment_id")
    private Double senderId;

    @Column(name = "receiver_payment_id")
    private Double receiverId;

    @Column(name = "payment_merchant")
    private Double paymentMerchant;

    @Column(name = "transaction_date")
    private Double transactionDate;

    @Column(name = "transaction_reference_number")
    private Double transactionReferenceNumber;

    @Column(name = "order_completed_date")
    private LocalDateTime orderCompletedDate;

    @Column(name = "return_policy_days")
    private Integer returnPolicyDays;

    @Column(name = "return_window_end")
    private LocalDate returnWindowEnd;
}
