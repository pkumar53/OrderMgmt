package com.ordermgmt.product_service.repository;

import com.ordermgmt.product_service.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
