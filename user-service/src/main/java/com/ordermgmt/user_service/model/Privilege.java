package com.ordermgmt.user_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "privs")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priv_id")
    private Long privId;

    @Column(name = "priv_name")
    private String privName;

    @Column(name = "description")
    private String description;
}
