package com.ordermgmt.authentication_service.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
