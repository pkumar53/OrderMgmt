package com.ordermgmt.order_service.dto;

import com.ordermgmt.order_service.model.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    private Long orderId;
    private User user;
    private Address address;
    private String orderReferenceNumber;
    private String orderStatus;
    private LocalDateTime deliveryDate;
    private LocalDateTime refundDate;
    private LocalDateTime orderDate;
    private Double totalAmountForOrder;
    private Double totalDiscountForOrder;
    private LocalDateTime orderCompletedDate;
    private List<OrderDetailRequestDTO> orderDetailReqDTOs = new ArrayList<>();
}
