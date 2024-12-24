package com.ordermgmt.order_service.dto;

import com.ordermgmt.order_service.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDetailRequestDTO {

    private Long orderDetailId;
    private Order order;
    private String status;
    private Product product;
    private Integer quantity;
    private Double discountPerQuantity;
    private Double discount;
    private Double discountPercentage;
    private Double pricePerQuantity;
    private Double price;
    private Double discountedPricePerQuantity;
    private Double discountedPrice;
    private Double amount;
    private Double amountPaid;
    private Double deliveryDate;
    private Double refundDate;
    private Double shippingDate;
    private Double returnDate;
    private Double exchangeDate;
    private Double paymentMode;
    private Double senderId;
    private Double receiverId;
    private Double paymentMerchant;
    private Double transactionDate;
    private Double transactionReferenceNumber;
    private LocalDateTime orderCompletedDate;
    private Integer returnPolicyDays;
    private LocalDate returnWindowEnd;
}
