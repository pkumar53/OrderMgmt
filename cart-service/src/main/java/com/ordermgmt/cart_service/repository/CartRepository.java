package com.ordermgmt.cart_service.repository;

import com.ordermgmt.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);

    List<Cart> findByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);
}
