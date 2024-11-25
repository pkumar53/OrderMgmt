package com.ordermgmt.order_service.controller;

import com.ordermgmt.order_service.dto.OrderRequestDTO;
import com.ordermgmt.order_service.exception.OrderNotFoundException;
import com.ordermgmt.order_service.model.Order;
import com.ordermgmt.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
