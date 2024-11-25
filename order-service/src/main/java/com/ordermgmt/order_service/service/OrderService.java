package com.ordermgmt.order_service.service;

import com.ordermgmt.order_service.dto.OrderRequestDTO;
import com.ordermgmt.order_service.exception.OrderNotFoundException;
import com.ordermgmt.order_service.model.Order;
import com.ordermgmt.order_service.model.OrderDetail;
import com.ordermgmt.order_service.repository.OrderDetailRepository;
import com.ordermgmt.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.getItems() == null || orderRequestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product.");
        }
        // Create Order
        Order order = new Order();
        order.setUserId(orderRequestDTO.getUserId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Placed");

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Create OrderDetails
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderRequestDTO.OrderItem item : orderRequestDTO.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setProductId(item.getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            detail.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            detail.setOrder(order);

            totalAmount = totalAmount.add(detail.getTotalPrice());
            orderDetails.add(detail);
        }

        order.setOrderDetails(orderDetails);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for user ID: " + userId);
        }
        return orders;
    }
}
