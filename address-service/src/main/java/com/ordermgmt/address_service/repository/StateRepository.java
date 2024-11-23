package com.ordermgmt.address_service.repository;

import com.ordermgmt.address_service.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findByCountryCountryId(Long countryId);
}
