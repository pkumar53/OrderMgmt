package com.ordermgmt.address_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "state")
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
