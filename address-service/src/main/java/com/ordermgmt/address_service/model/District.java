package com.ordermgmt.address_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "district")
@Getter
@Setter
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "district")
    private String district;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
