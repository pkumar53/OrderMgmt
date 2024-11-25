package com.ordermgmt.authentication_service.service;

import com.ordermgmt.authentication_service.dto.AuthRequest;
import com.ordermgmt.authentication_service.dto.AuthResponse;
import com.ordermgmt.authentication_service.dto.RegisterRequest;
import com.ordermgmt.authentication_service.model.AuthToken;
import com.ordermgmt.authentication_service.repository.AuthTokenRepository;
import com.ordermgmt.authentication_service.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    public AuthResponse login(AuthRequest request) {
        // Call ProfileService to validate the user credentials
        String userServiceUrl = "http://localhost:8082/users/validate";
        Boolean isValidUser = restTemplate.postForObject(userServiceUrl, request, Boolean.class);

        if (isValidUser) {
            String token = jwtUtils.generateToken(request.getUsername());
            // Save the token to the database (optional)
            AuthToken authToken = new AuthToken();
            authToken.setUsername(request.getUsername());
            authToken.setToken(token);
            authTokenRepository.save(authToken);
            return new AuthResponse(token);
        }

        throw new RuntimeException("Invalid credentials");
    }

    // Call UserService to register the user
    public String register(RegisterRequest request) {
        String userServiceUrl = "http://localhost:8082/users/register";
        return restTemplate.postForObject(userServiceUrl, request, String.class);
    }

    public boolean validateToken(String token) {
        AuthToken authToken = authTokenRepository.findByToken(token);
        return authToken != null;  // This means the token exists in the DB
    }
}
