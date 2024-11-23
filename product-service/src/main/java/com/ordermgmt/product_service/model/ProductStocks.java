package com.ordermgmt.product_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_stocks")
@Getter
@Setter
public class ProductStocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_stock_id")
    private Long productStockId;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQuantity;

    @Column(name = "reserved_stock", nullable = false)
    private Integer reservedStock;
}
