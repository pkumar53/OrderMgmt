import React, { useState, useEffect } from "react";

function DeliveryAddress() {
  const [shippingAddress, setShippingAddress] = useState({});
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchShippingAddress = async () => {
      try {
        const response = await fetch(
          "http://localhost:8081/addresses/1/shippingAddress"
        );
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setShippingAddress(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchShippingAddress();
  }, []);
  if (loading) {
    return <p>Loading Address...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const editAddress = (shipAddress) => {
    console.log("Address editing is pending");
    window.location.href='address';
  }
  return (
    <div className="delivery mt-40">
      <h4>Delivery Address</h4>
      <div className="mt-20">
        <p className="desc">
          {shippingAddress.addressDetail}, {shippingAddress.areaName},{" "}
          {shippingAddress.city.cityName}
        </p>
        <p className="desc">
          {shippingAddress.district.district}, {shippingAddress.state.stateName}
          , {shippingAddress.country.countryName}
        </p>
        <p className="desc">{shippingAddress.zipCode}</p>
      </div>
      <button className="btn edtAdd" onClick={()=>editAddress(shippingAddress)}>Edit</button>
    </div>
  );
}

export default DeliveryAddress;
