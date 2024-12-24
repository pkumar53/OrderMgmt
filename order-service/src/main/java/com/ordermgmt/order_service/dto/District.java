package com.ordermgmt.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class District {
    private Long districtId;
    private String district;
    private State state;
}
