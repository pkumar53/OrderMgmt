package com.ordermgmt.cart_service.controller;

import com.ordermgmt.cart_service.dto.CartDto;
import com.ordermgmt.cart_service.model.Cart;
import com.ordermgmt.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCartsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartsByUserId(userId));
    }

    //not used yet
    @GetMapping("/getCartByProductId")
    public ResponseEntity<CartDto> getCartByUserIdAndProductId(@PathVariable Long userId, @RequestParam("productId") Long productId) {
        return ResponseEntity.ok(cartService.getCartByUserIdAndProductId(userId, productId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartCountForUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartCountByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<CartDto> addOrUpdateProduct(@RequestBody CartDto cartDto) {
        return ResponseEntity.ok(cartService.addOrUpdateProductInCart(cartDto));
    }

    @PostMapping("/addSelectedProduct")
    public void addProductToCart(@RequestBody CartDto cartDto) {
        cartService.addSelectedProductToCart(cartDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeProduct(@RequestBody CartDto cartDto) {
        cartService.removeProductFromCart(cartDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCartsByUserId(@PathVariable Long userId) {
        cartService.deleteAllCartsByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
