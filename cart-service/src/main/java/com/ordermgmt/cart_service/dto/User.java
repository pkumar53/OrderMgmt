package com.ordermgmt.cart_service.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;
    private String userShortName;
    private String userFullName;
    private String email;
    private Long phoneNumber;
    private String loginId;
    private String password;
    private String status;
    private Long loginAttempt;
}
