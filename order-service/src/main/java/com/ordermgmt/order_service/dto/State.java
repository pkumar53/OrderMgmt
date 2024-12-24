package com.ordermgmt.order_service.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {
    private Long stateId;
    private String stateName;
    private Country country;
}
