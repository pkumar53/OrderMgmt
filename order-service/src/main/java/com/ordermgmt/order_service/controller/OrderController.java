package com.ordermgmt.order_service.controller;

import com.ordermgmt.order_service.dto.OrderRequestDTO;
import com.ordermgmt.order_service.dto.PlaceOrderRequestDTO;
import com.ordermgmt.order_service.exception.OrderNotFoundException;
import com.ordermgmt.order_service.model.Order;
import com.ordermgmt.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO) {
        OrderRequestDTO orderRequestDTO = orderService.createOrder(placeOrderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRequestDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderRequestDTO> orderRequestDTOs = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderRequestDTOs);
    }
}
