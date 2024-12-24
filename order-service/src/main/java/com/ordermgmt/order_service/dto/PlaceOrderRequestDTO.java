package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequestDTO {
    private List<CartDto> cartItems;
    private Long userId;
    private Long shippingAddressId;
    private Double shippingFee;
    private Double packagingCharge;
    private Double totalAmount;
    private Double totalDiscount;
}
