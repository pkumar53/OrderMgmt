import React, { useEffect, useState } from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import CartCount from "./CartCount";
import "./Address.css";
import { useNavigate } from "react-router-dom";

function Address() {
  const [activeTab, setActiveTab] = useState("user");
  const { cartCount } = CartCount();
  const [addresses, setAddresses] = useState([]);
  const [loading, setLoading] = useState("");
  const [error, setError] = useState("");
  const [selectedAddress, setSelectedAddress] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserAddresses = async () => {
      try {
        const response = await fetch("http://localhost:8081/user/1/addresses");
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setAddresses(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserAddresses();
  }, []);

  if (loading) {
    return <p>Loading cart...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const handleAddressClick = (event) => {
    const address = addresses.find((item) => item.addressId === Number(event.target.value));
    setSelectedAddress(address);
  };

  const deleteAddress = async () => {
    if(!selectedAddress) return;

    try {
      const response = await fetch(`http://localhost:8081/user/1/addresses/${selectedAddress.addressId}`, {
        method: "DELETE"
      });
      if (response.ok) {
        // Update the list by removing the deleted address
        setAddresses(addresses.filter((item) => item.addressId !== selectedAddress.addressId));
        setSelectedAddress(null);
        alert("Address removed successfully!");
      } else {
        alert("Failed to remove address. Please try again.");
      }
    } catch (error) {
      console.error("Error deleting address:", error);
      alert("An error occurred while removing the address.");
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
          <div className="cartleft fleft mt-10 ml-10">
            {addresses.length > 0 ? (
              <div className="order">
                {addresses.map((address) => (
                  <div className="addressBox">
                    <div className="radioDiv fleft">
                      <input
                        type="radio"
                        name="addressRadio"
                        onChange={(event) => handleAddressClick(event)}
                        value={address.addressId}
                      />
                    </div>
                    <div className="addrDiv fleft ml-20">
                      <span className="addressType">{address.addressType}</span>
                      <p>
                        {address.addressDetail} {address.areaName}{" "}
                        {address.city.cityName}
                      </p>
                      <p>
                        {address.district.district} {address.state.stateName}{" "}
                        {address.country.countryName}
                      </p>
                      <p>{address.zipCode}</p>
                      <br />
                      <p>{address.userId}User name and phone</p>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="order">
                <h1 style={{ textAlign: "center" }} className="mt-40">
                  No Addresses added yet.
                </h1>
                <div className="mt-40">
                  <button
                    className="btn placeOrderBtn mt-20"
                    style={{ width: "200px" }}
                    onClick={() => (window.location.href = "medicines")}
                  >
                    Add Medicines
                  </button>
                </div>
              </div>
            )}
          </div>
          <div className="cartright bill fleft mt-10 pad-20 ml-10">
            <button className="btn placeOrderBtn addressBtn mb-20" onClick={()=>navigate('/addOrEditAddress')}>Add New Address</button>
            <br />
            {selectedAddress && (
              <>
                <button className="btn placeOrderBtn addressBtn mb-20" onClick={()=>navigate(`/addOrEditAddress?addressId=${selectedAddress.addressId}`)}>Edit Address</button>
                <br />
                <button className="btn placeOrderBtn addressBtn mb-20" onClick={() => deleteAddress()}>Remove Address</button>
                <br />
                <button className="btn placeOrderBtn addressBtn mb-20" onClick={()=>navigate(`/cart?addressId=${selectedAddress.addressId}`)}>Continue to Cart</button>
                <br />
              </>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Address;
