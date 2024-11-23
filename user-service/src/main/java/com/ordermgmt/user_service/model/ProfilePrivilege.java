package com.ordermgmt.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profprivs")
@Getter
@Setter
public class ProfilePrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prof_priv_id")
    private Long profPrivId;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "priv_id", nullable = false)
    private Privilege privilege;
}
