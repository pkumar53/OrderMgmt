package com.ordermgmt.user_service.repository;

import com.ordermgmt.user_service.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
