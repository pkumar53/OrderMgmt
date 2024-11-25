package com.ordermgmt.cart_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private double totalPrice;
    private Boolean checkedOut;
    private Timestamp checkOutDate;
}
