package com.ordermgmt.cart_service.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long stateId;
    @Column(name = "state_name")
    private String stateName;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
