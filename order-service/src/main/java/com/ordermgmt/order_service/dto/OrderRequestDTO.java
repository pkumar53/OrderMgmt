package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private Long userId;
    private List<OrderItem> items;

    @Getter
    @Setter
    public static class OrderItem {
        private Long productId;
        private Integer quantity;
        private BigDecimal price;
    }
}
