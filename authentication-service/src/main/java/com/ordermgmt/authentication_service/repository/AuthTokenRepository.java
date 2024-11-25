package com.ordermgmt.authentication_service.repository;

import com.ordermgmt.authentication_service.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByUsername(String username);
    AuthToken findByToken(String token);
}
