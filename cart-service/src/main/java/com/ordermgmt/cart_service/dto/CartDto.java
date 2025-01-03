package com.ordermgmt.cart_service.dto;

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
    private double price;
    private double discount;
    private double discountedPrice;
    private Double discountPercentage;
    private Boolean checkedOut;
    private Timestamp checkOutDate;
}
