package com.ordermgmt.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_short_name")
    private String userShortName;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "passwd")
    private String password;

    @OneToOne
    @JoinColumn(name = "prof_id", nullable = false)
    private Profile profile;

    @Column(name = "status")
    private String status;

    @Column(name = "failed_login_attempts")
    private Long loginAttempt;
}
