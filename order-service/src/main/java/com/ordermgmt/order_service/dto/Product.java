package com.ordermgmt.order_service.dto;

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
    private Double discount;
    private Double discountPercentage;
    private Double discountedPrice;
}
