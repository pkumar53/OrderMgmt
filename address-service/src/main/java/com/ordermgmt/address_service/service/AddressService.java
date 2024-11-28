package com.ordermgmt.address_service.service;

import com.ordermgmt.address_service.dto.User;
import com.ordermgmt.address_service.model.*;
import com.ordermgmt.address_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE = "http://USER-SERVICE/users/";

    // Get all addresses of a user
    public List<Address> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    // Add a new address
    public Address addAddress(Address address) {
        ResponseEntity<User> userResponse = restTemplate.getForEntity(USER_SERVICE + address.getUserId(), User.class);

        if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
            throw new RuntimeException("User not found");
        }
        if (!countryRepository.existsById(address.getCountry().getCountryId())) {
            throw new RuntimeException("Invalid country");
        }
        if (!stateRepository.existsById(address.getState().getStateId())) {
            throw new RuntimeException("Invalid state");
        }
        if (!districtRepository.existsById(address.getDistrict().getDistrictId())) {
            throw new RuntimeException("Invalid district");
        }
        if (!cityRepository.existsById(address.getCity().getCityId())) {
            throw new RuntimeException("Invalid city");
        }

        // Associate the user to the address
        address.setUserId(userResponse.getBody().getUserId());

        return addressRepository.save(address);
    }

    // Update an existing address
    public Address updateAddress(Long addressId, Address addressDetails) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
        address.setAddressDetail(addressDetails.getAddressDetail());
        address.setCity(addressDetails.getCity());
        address.setZipCode(addressDetails.getZipCode());
        address.setCountry(addressDetails.getCountry());
        address.setState(addressDetails.getState());
        address.setDistrict(addressDetails.getDistrict());
        return addressRepository.save(address);
    }

    // Delete an address
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    // Fetch all countries
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    // Fetch all states by country ID
    public List<State> getStatesByCountry(Long countryId) {
        return stateRepository.findByCountryCountryId(countryId);
    }

    // Fetch all districts by state ID
    public List<District> getDistrictsByState(Long stateId) {
        return districtRepository.findByStateStateId(stateId);
    }

    // Fetch all cities by district ID
    public List<City> getCitiesByDistrict(Long districtId) {
        return cityRepository.findByDistrictDistrictId(districtId);
    }

    public Address getShippingAddressForUser(Long userId) {
        return addressRepository.findByUserIdAndAddressType(userId, "mailing");
    }
}