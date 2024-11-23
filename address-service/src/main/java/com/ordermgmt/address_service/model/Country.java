package com.ordermgmt.address_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "country_name")
    private String countryName;
}
