package com.ordermgmt.cart_service.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class District {
    private Long districtId;
    private String district;
    private State state;
}
