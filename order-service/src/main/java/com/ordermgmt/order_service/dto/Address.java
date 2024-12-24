package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private Long addressId;
    private Long userId;
    private String areaName;
    private String zipCode;
    private String addressDetail;
    private String status;
    private String addressType;
    private Country country;
    private State state;
    private District district;
    private City city;
}
