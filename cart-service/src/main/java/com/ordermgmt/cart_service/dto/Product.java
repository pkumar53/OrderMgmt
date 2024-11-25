package com.ordermgmt.cart_service.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long productId;
    private String productShortName;
    private String productName;
    private String description;
    private Double pricePerQty;
    private String brandName;
    private String productForm;
    private ProductType productType;
    private String imageUrl;
    private String tabletsPerStrip;
    private double discount;
}
