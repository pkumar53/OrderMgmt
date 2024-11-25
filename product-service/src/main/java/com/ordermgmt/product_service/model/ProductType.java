package com.ordermgmt.product_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "productType")
@Getter
@Setter
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_type_id")
    private Long productTypeId;

    @Column(name = "type_name")
    private String productTypeName;

    @OneToOne
    @JoinColumn(name = "product_parent_type_id", nullable = true, unique = false)
    private ProductType parentProductType;
}
