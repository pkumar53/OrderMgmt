package com.ordermgmt.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDTO {
    private Long orderId;
    private Long userId;
    private String status; // e.g., "PLACED", "SHIPPED", "DELIVERED"
}
