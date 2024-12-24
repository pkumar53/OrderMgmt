import React, { useEffect, useState } from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import CartCount from "./CartCount";
import "./common.css";
import "./Address.css";
import { useLocation } from "react-router-dom";

function AddOrEditAddress() {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const addressId = searchParams.get("addressId");

  const [activeTab, setActiveTab] = useState("user");
  const { cartCount } = CartCount();
  const [loading, setLoading] = useState("");
  const [error, setError] = useState("");
  const [cities, setCities] = useState([]);
  const [states, setStates] = useState([]);
  const [countries, setCountries] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [stateDiv, setStateDiv] = useState(false);
  const [districtDiv, setDistrictDiv] = useState(false);
  const [cityDiv, setCityDiv] = useState(false);
  const [address, setAddress] = useState({
    userId: 1,
    addressDetail: "",
    areaName: "",
    addressType: "",
    city: {
      cityId: "",
    },
    district: {
      districtId: "",
    },
    state: {
      stateId: "",
    },
    country: {
      countryId: "",
    },
  });

  useEffect(() => {
    if (addressId) {
      console.log("Address id fetched is "+addressId);
      const fetchAddress = async () => {
        try {
          const response = await fetch("http://localhost:8081/user/1/addresses/"+addressId);
          if (response.ok) {
            const data = await response.json();
            setAddress(data);
            fetchStates(data.country.countryId);
            fetchDistricts(data.district.districtId);
            fetchCities(data.city.cityId);
          } else {
            setError("Failed to fetch countries.");
          }
        } catch (error) {
          setError("Failed to fetch countries.");
          alert("An error occurred while saving the address.");
        }
      };
      fetchAddress();
    }
    const fetchCountries = async () => {
      try {
        const response = await fetch("http://localhost:8081/user/1/addresses/countries");
        if (response.ok) {
          const data = await response.json();
          setCountries(data);
        } else {
          setError("Failed to fetch countries.");
        }
      } catch (error) {
        setError("Failed to fetch countries.");
        alert("An error occurred while saving the address.");
      }
    };

    fetchCountries();
  }, [addressId]);

  if (loading) {
    return <p>Loading cart...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const saveAddress = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8081/user/1/addresses/add", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(address),
      });
      if (response.ok) {
        alert("Address saved successfully!");
        window.location.href="address"
      } else {
        alert("Failed to save address. Please try again.");
      }
    } catch (error) {
      console.error("Error saving address:", error);
      alert("An error occurred while saving the address.");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    const keys = name.split(".");
    setAddress((prev) => {
      if (keys.length === 1) {
        return { ...prev, [keys[0]]: value };
      }
      const [firstKey, secondKey] = keys;
      return {
        ...prev,
        [firstKey]: { ...prev[firstKey], [secondKey]: value },
      };
    });
    if (keys[0] === "country") {
      fetchStates(value);
    }
    if (keys[0] === "state") {
      fetchDistricts(value);
    }
    if (keys[0] === "district") {
      fetchCities(value);
    }
  };
  const fetchStates = async (value) => {
    setStateDiv(false);
    setDistrictDiv(false);
    setCityDiv(false);

    try {
      const response = await fetch("http://localhost:8081/user/1/addresses/states?countryId="+value);
      if (response.ok) {
        const data = await response.json();
        if (data && data.length > 0) {
          setStateDiv(true);
          setStates(data);
        }
      } else {
          setError("Failed to fetch countries.");
      }
    } catch (error) {
      setError("Failed to fetch countries.");
      alert("An error occurred while saving the address.");
    }
  };

  const fetchDistricts = async (value) => {
    setDistrictDiv(false);
    setCityDiv(false);

    try {
      const response = await fetch("http://localhost:8081/user/1/addresses/districts?stateId="+value);
      if (response.ok) {
        const data = await response.json();
        if (data && data.length > 0) {
          setDistrictDiv(true);
          setDistricts(data);
        }
      } else {
        setError("Failed to fetch countries.");
      }
    } catch (error) {
      setError("Failed to fetch countries.");
      alert("An error occurred while saving the address.");
    }
  };

  const fetchCities = async (value) => {
    setCityDiv(false);

    try {
      const response = await fetch("http://localhost:8081/user/1/addresses/cities?districtId="+value);
      if (response.ok) {
        const data = await response.json();
        if (data && data.length > 0) {
          setCityDiv(true);
          setCities(data);
        }
      } else {
        setError("Failed to fetch countries.");
      }
    } catch (error) {
      setError("Failed to fetch countries.");
      alert("An error occurred while saving the address.");
    }
  };

  return (
    <div>
      <Navbar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        cartCount={cartCount}
      />
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">User Address</h3>
        <div className="cartContainer">
          <form style={{ minHeight: "inherit" }} onSubmit={saveAddress}>
            <div className="cartleft fleft mt-10 ml-10">
              <div className="order">
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">
                    Address Line 1
                  </div>
                  <div className="inpDiv">
                    <input
                      className="input"
                      type="text"
                      name="addressDetail"
                      value={address.addressDetail}
                      onChange={handleChange}
                      required
                    ></input>
                  </div>
                </div>
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">
                    Address Line 2
                  </div>
                  <div className="inpDiv">
                    <input
                      className="input"
                      type="text"
                      name="areaName"
                      value={address.areaName}
                      onChange={handleChange}
                      required
                    ></input>
                  </div>
                </div>
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">Country</div>
                  <div className="inpDiv">
                    <select
                      className="input"
                      name="country.countryId"
                      value={address.country.countryId}
                      onChange={handleChange}
                      required
                    >
                      <option value="">Select Country</option>
                      {countries.map((country, index) => (
                        <option key={index} value={country.countryId}>
                          {country.countryName}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                {stateDiv && (
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">State</div>
                  <div className="inpDiv">
                    <select
                      className="input"
                      name="state.stateId"
                      value={address.state.stateId}
                      onChange={handleChange}
                      required
                    >
                      <option value="">Select State</option>
                      {states.map((state, index) => (
                        <option key={index} value={state.stateId}>
                          {state.stateName}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                )}
                {districtDiv && (
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">District</div>
                  <div className="inpDiv">
                    <select
                      className="input"
                      name="district.districtId"
                      value={address.district.districtId}
                      onChange={handleChange}
                      required
                    >
                      <option value="">Select District</option>
                      {districts.map((district, index) => (
                        <option key={index} value={district.districtId}>
                          {district.district}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                )}
                {cityDiv && (
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">City</div>
                  <div className="inpDiv">
                    <select
                      className="input"
                      name="city.cityId"
                      value={address.city.cityId}
                      onChange={handleChange}
                      required
                    >
                      <option value="">Select City</option>
                      {cities.map((city, index) => (
                        <option key={index} value={city.cityId}>
                          {city.city}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                )}
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">Zip</div>
                  <div className="inpDiv">
                    <input
                      className="input"
                      type="text"
                      name="zipCode"
                      value={address.zipCode}
                      onChange={handleChange}
                      required
                    ></input>
                  </div>
                </div>
                <div className="addressInpDiv">
                  <div className="fleft labelDiv aligncenter">Address Type</div>
                  <div className="inpDiv">
                    <input
                      className="input fleft"
                      name="addressType"
                      type="radio"
                      value={"home"}
                      checked={address.addressType === 'home'}
                      onChange={handleChange}
                    ></input>
                    <p className="fleft ml-10 mt-10">Home</p>
                    <input
                      className="input ml-20 fleft"
                      name="addressType"
                      type="radio"
                      checked={address.addressType === 'work'}
                      value={"work"}
                      onChange={handleChange}
                    ></input>
                    <p className="fleft ml-10 mt-10">Work</p>
                    <input
                      className="input ml-20 fleft"
                      name="addressType"
                      type="radio"
                      checked={address.addressType === 'office'}
                      value={"office"}
                      onChange={handleChange}
                    ></input>
                    <p className="fleft ml-10 mt-10">Office</p>
                    <input
                      className="input ml-20 fleft"
                      name="addressType"
                      type="radio"
                      checked={address.addressType === 'other'}
                      value={"other"}
                      onChange={handleChange}
                    ></input>
                    <p className="fleft ml-10 mt-10">Others</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="cartright bill fleft mt-10 pad-20 ml-10">
              <button
                className="btn placeOrderBtn addressBtn mb-20 mt-40"
                type="submit"
              >
                Save Address
              </button>
              <br />
              <button
                className="btn placeOrderBtn addressBtn mb-20 mt-40"
                type="reset"
              >
                Reset
              </button>
            </div>
          </form>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default AddOrEditAddress;
