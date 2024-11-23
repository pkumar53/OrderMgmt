package com.ordermgmt.user_service.repository;

import com.ordermgmt.user_service.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
