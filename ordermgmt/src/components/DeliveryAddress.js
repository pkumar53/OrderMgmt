import React, { useState, useEffect } from "react";

function DeliveryAddress(props) {
  const [shippingAddress, setShippingAddress] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (props.shippingAddressId) {
      const fetchShippingAddress = async () => {
        try {
          const response = await fetch(
            "http://localhost:8081/user/1/addresses/" + props.shippingAddressId
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
    }
  }, []);
  if (props.shippingAddressId && loading) {
    return <p>Loading Address...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  return (
    <div className="delivery mt-40">
      <h4>Delivery Address</h4>
      {shippingAddress ? (
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
        <button className="btn placeOrderBtn edtAdd" onClick={()=>window.location.href='address'}>Edit</button><br />
        <button className="btn placeOrderBtn edtAdd mt-40" onClick={()=>window.location.href='address'}>Another Address</button>
      </div>
      ) : (
        <button className="btn placeOrderBtn edtAdd mt-40" onClick={()=>window.location.href='address'}>Select Address</button>
      )}
      
    </div>
  );
}

export default DeliveryAddress;
