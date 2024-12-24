package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductType {
    private Long productTypeId;
    private String productTypeName;
    private ProductType parentProductType;
}
