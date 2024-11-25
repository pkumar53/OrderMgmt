package com.ordermgmt.cart_service.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductType {
    private Long productTypeId;
    private String productTypeName;
    private ProductType parentProductType;
}
