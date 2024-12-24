package com.ordermgmt.order_service.service;

import com.ordermgmt.order_service.OrderStatus;
import com.ordermgmt.order_service.dto.*;
import com.ordermgmt.order_service.exception.OrderNotFoundException;
import com.ordermgmt.order_service.model.Order;
import com.ordermgmt.order_service.model.OrderDetail;
import com.ordermgmt.order_service.repository.OrderDetailRepository;
import com.ordermgmt.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private RestTemplate restTemplate;

    public OrderRequestDTO createOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
        if (placeOrderRequestDTO.getCartItems() == null || placeOrderRequestDTO.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product.");
        }

        //The below fields for order will be set while placing an order. There are few fields which are not set during
        // this flow because they don't fit the scenario.
        // Create Order
        Order order = new Order();
        order.setUserId(placeOrderRequestDTO.getUserId());
        order.setAddressId(placeOrderRequestDTO.getShippingAddressId());
        order.setOrderReferenceNumber("ORDR" + UUID.randomUUID().toString().substring(0, 8));
        order.setOrderStatus(OrderStatus.PLACED.toString());
//        order.setDeliveryDate(); /* this is pending */
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmountForOrder(placeOrderRequestDTO.getTotalAmount());
        order.setTotalDiscountForOrder(placeOrderRequestDTO.getTotalDiscount());
        // Create OrderDetails
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDto cartDTO : placeOrderRequestDTO.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            orderDetail.setStatus(OrderStatus.PLACED.toString());
            orderDetail.setProductId(cartDTO.getProduct().getProductId());
            orderDetail.setQuantity(cartDTO.getQuantity());

            orderDetail.setDiscount(cartDTO.getDiscount());
            orderDetail.setDiscountPercentage(cartDTO.getDiscountPercentage());
            orderDetail.setDiscountedPrice(cartDTO.getDiscountedPrice());
            orderDetail.setPrice(cartDTO.getPrice());

            orderDetail.setAmount(cartDTO.getDiscountedPrice() /* + shipping and packaging charge to be added*/);

            orderDetail.setDiscountPerQuantity(cartDTO.getProduct().getDiscount());
            orderDetail.setPricePerQuantity(cartDTO.getProduct().getPricePerQty());
            orderDetail.setDiscountedPricePerQuantity(cartDTO.getProduct().getDiscountedPrice());

//            orderDetail.setAmountPaid();  /* payment is yet to be implemented */
//            orderDetail.setPaymentMode(placeOrderRequestDTO.get);
//            if payment mode online {
//            orderDetail.setSenderId
//            orderDetail.setReceiverId();
//            orderDetail.setPaymentMerchant();
//            orderDetail.setTransactionDate();
//            orderDetail.setTransactionReferenceNumber();
//            }
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        Order orderSaved = orderRepository.save(order);
        if (orderSaved.getOrderId() > 0) {
            restTemplate.delete("http://localhost:8084/user/{userId}/carts/deleteAll", order.getUserId());
        }
        User user = new User();// dummy user object created as user object is not required here yet.
        user.setUserId(order.getUserId());
        return mapOrderToOrderRequestDTO(orderSaved, user);
    }

    public List<OrderRequestDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        User user = restTemplate.getForObject("http://localhost:8082/users/{userId}", User.class, userId);
        return orders.stream().map(order -> mapOrderToOrderRequestDTO(order, user)).collect(Collectors.toList());
    }

    public OrderRequestDTO mapOrderToOrderRequestDTO(Order order, User user) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setOrderId(order.getOrderId());
        orderRequestDTO.setUser(user);
        orderRequestDTO.setOrderDate(order.getOrderDate());
        orderRequestDTO.setOrderStatus(order.getOrderStatus());
        orderRequestDTO.setOrderReferenceNumber(order.getOrderReferenceNumber());
        orderRequestDTO.setOrderCompletedDate(order.getOrderCompletedDate());
        Address address = restTemplate.getForObject("http://localhost:8081/user/{userId}/addresses/{addressId}", Address.class, user.getUserId(), order.getAddressId());
        orderRequestDTO.setAddress(address);
        orderRequestDTO.setTotalDiscountForOrder(order.getTotalDiscountForOrder());
        orderRequestDTO.setTotalAmountForOrder(order.getTotalAmountForOrder());
        orderRequestDTO.setDeliveryDate(order.getDeliveryDate());
        orderRequestDTO.setRefundDate(order.getRefundDate());
        orderRequestDTO.setOrderDetailReqDTOs(mapOrderDetailsToOrderDetailRequestDTOs(order.getOrderDetails()));
        return orderRequestDTO;
    }

    private List<OrderDetailRequestDTO> mapOrderDetailsToOrderDetailRequestDTOs(List<OrderDetail> orderDetails) {

        return orderDetails.stream().map(orderDetail -> {
            OrderDetailRequestDTO orderDetailRequestDTO = new OrderDetailRequestDTO();
            orderDetailRequestDTO.setAmount(orderDetail.getAmount());
            orderDetailRequestDTO.setAmountPaid(orderDetail.getAmountPaid());
            orderDetailRequestDTO.setDeliveryDate(orderDetail.getDeliveryDate());
            orderDetailRequestDTO.setDiscountedPrice(orderDetail.getDiscountedPrice());
            orderDetailRequestDTO.setDiscount(orderDetail.getDiscount());
            orderDetailRequestDTO.setDiscountPercentage(orderDetail.getDiscountPercentage());
            orderDetailRequestDTO.setDiscountedPricePerQuantity(orderDetail.getDiscountedPricePerQuantity());
            orderDetailRequestDTO.setDiscountPerQuantity(orderDetail.getDiscountPerQuantity());
            orderDetailRequestDTO.setExchangeDate(orderDetail.getExchangeDate());
            orderDetailRequestDTO.setOrderDetailId(orderDetail.getOrderDetailId());
            orderDetailRequestDTO.setOrderCompletedDate(orderDetail.getOrderCompletedDate());
            orderDetailRequestDTO.setPrice(orderDetail.getPrice());
            orderDetailRequestDTO.setPaymentMode(orderDetail.getPaymentMode());
            orderDetailRequestDTO.setPaymentMerchant(orderDetail.getPaymentMerchant());
            orderDetailRequestDTO.setProductId(orderDetail.getProductId());
            orderDetailRequestDTO.setPricePerQuantity(orderDetail.getPricePerQuantity());
            orderDetailRequestDTO.setQuantity(orderDetail.getQuantity());
            orderDetailRequestDTO.setReceiverId(orderDetail.getReceiverId());
            orderDetailRequestDTO.setRefundDate(orderDetail.getRefundDate());
            orderDetailRequestDTO.setReturnDate(orderDetail.getReturnDate());
            orderDetailRequestDTO.setReturnWindowEnd(orderDetail.getReturnWindowEnd());
            orderDetailRequestDTO.setReturnPolicyDays(orderDetail.getReturnPolicyDays());
            orderDetailRequestDTO.setStatus(orderDetail.getStatus());
            orderDetailRequestDTO.setSenderId(orderDetail.getSenderId());
            orderDetailRequestDTO.setShippingDate(orderDetail.getShippingDate());
            orderDetailRequestDTO.setTransactionDate(orderDetail.getTransactionDate());
            orderDetailRequestDTO.setTransactionReferenceNumber(orderDetail.getTransactionReferenceNumber());
            return orderDetailRequestDTO;
        }).collect(Collectors.toList());
    }
}
