package com.ordermgmt.address_service.repository;

import com.ordermgmt.address_service.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByDistrictDistrictId(Long districtId);
}
