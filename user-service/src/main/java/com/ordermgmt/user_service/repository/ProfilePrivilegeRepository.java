package com.ordermgmt.user_service.repository;

import com.ordermgmt.user_service.model.ProfilePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePrivilegeRepository extends JpaRepository<ProfilePrivilege, Long> {
    List<ProfilePrivilege> findAllByProfile_ProfileId(Long profileId);
}
