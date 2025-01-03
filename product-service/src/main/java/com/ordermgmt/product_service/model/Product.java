package com.ordermgmt.product_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_short_name", nullable = false)
    private String productShortName;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_qty", nullable = false)
    private Double pricePerQty;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "product_form")
    private String productForm;

    @ManyToOne
    @JoinColumn(name = "prod_type_id", nullable = false)
    private ProductType productType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "tablets_per_strip")
    private String tabletsPerStrip;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "discount_pct")
    private Double discountPercentage;

    @Column(name = "discountedPrice")
    private Double discountedPrice;
}
