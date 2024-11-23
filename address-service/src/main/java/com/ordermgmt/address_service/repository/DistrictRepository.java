package com.ordermgmt.address_service.repository;

import com.ordermgmt.address_service.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByStateStateId(Long stateId);
}
