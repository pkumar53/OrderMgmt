package com.ordermgmt.address_service.controller;

import com.ordermgmt.address_service.model.*;
import com.ordermgmt.address_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    // Get all addresses for a user
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    // Add a new address
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addAddress(address));
    }

    // Update an existing address
    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, address));
    }

    // Delete an address
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    // Get all countries
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(addressService.getAllCountries());
    }

    // Get states by country ID
    @GetMapping("/states")
    public ResponseEntity<List<State>> getStatesByCountry(@RequestParam("countryId") Long countryId) {
        return ResponseEntity.ok(addressService.getStatesByCountry(countryId));
    }

    // Get districts by state ID
    @GetMapping("/districts")
    public ResponseEntity<List<District>> getDistrictsByState(@RequestParam("stateId") Long stateId) {
        return ResponseEntity.ok(addressService.getDistrictsByState(stateId));
    }

    // Get cities by district ID
    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCitiesByDistrict(@RequestParam("districtId") Long districtId) {
        return ResponseEntity.ok(addressService.getCitiesByDistrict(districtId));
    }
}
