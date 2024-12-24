package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private Long cityId;
    private String city;
    private District district;
}
