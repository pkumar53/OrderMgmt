package com.ordermgmt.address_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "addr_dtl")
    private String addressDetail;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
