package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CartDto {
    private Long cartId;
    private User user;
    private Product product;
    private Timestamp addDate;
    private int quantity;
    private double price;//mrp
    private double discount;
    private double discountedPrice;//price after discount
    private Double discountPercentage;
    private Boolean checkedOut;
    private Timestamp checkOutDate;
}
