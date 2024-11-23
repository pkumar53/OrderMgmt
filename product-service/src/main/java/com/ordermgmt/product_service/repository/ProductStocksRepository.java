package com.ordermgmt.product_service.repository;

import com.ordermgmt.product_service.model.ProductStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStocksRepository extends JpaRepository<ProductStocks, Long> {
    ProductStocks findByProduct_ProductId(Long productId);
}
