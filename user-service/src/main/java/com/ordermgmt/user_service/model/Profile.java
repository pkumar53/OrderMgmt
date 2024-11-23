package com.ordermgmt.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prof_id")
    private Long profileId;

    @Column(name = "prof_name")
    private String profileName;

    @Column(name = "description")
    private String description;
}
