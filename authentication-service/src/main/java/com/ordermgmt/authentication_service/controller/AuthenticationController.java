package com.ordermgmt.authentication_service.controller;

import com.ordermgmt.authentication_service.dto.AuthRequest;
import com.ordermgmt.authentication_service.dto.AuthResponse;
import com.ordermgmt.authentication_service.dto.RegisterRequest;
import com.ordermgmt.authentication_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @GetMapping("/validate-token")
    public boolean validateToken(@RequestParam String token) {
        return authenticationService.validateToken(token);
    }
}
