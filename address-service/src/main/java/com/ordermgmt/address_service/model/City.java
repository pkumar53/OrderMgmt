package com.ordermgmt.address_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "city")
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
