package com.ordermgmt.address_service.repository;

import com.ordermgmt.address_service.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
