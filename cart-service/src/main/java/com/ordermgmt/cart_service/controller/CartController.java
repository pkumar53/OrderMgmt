package com.ordermgmt.cart_service.controller;

import com.ordermgmt.cart_service.dto.CartDto;
import com.ordermgmt.cart_service.model.Cart;
import com.ordermgmt.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> addOrUpdateProduct(@RequestBody CartDto cartDto) {
        return ResponseEntity.ok(cartService.addOrUpdateProductInCart(cartDto));
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<CartDto>> getAllCartsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @GetMapping("{userId}/count")
    public ResponseEntity<Integer> getCartCountForUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartCountByUserId(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeProduct(@RequestBody CartDto cartDto) {
        cartService.removeProductFromCart(cartDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartDto> getCardByUserIdAndProductId(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        return ResponseEntity.ok(cartService.getCartByUserIdAndProductId(userId, productId));
    }

    @PostMapping("/addSelectedProduct")
    public void addProductToCart(@RequestBody CartDto cartDto) {
        cartService.addSelectedProductToCart(cartDto);
    }
}
