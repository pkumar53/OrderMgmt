package com.ordermgmt.cart_service.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private Long cityId;
    private String city;
    private District district;
}
