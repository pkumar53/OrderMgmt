import React, { useEffect, useState } from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import CartCount from "./CartCount";

function Address() {
  const [activeTab, setActiveTab] = useState("user");
  const { cartCount, setCartCount } = CartCount();
  const [addresses, setAddresses] = useState([]);
  const [loading, setLoading] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUserAddresses = async () => {
      try {
        const response = await fetch("http://localhost:8081/addresses?userId=1");
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

  return (
    <div>
      <Navbar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        cartCount={cartCount}
      />
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">Address</h3>
        <div className="cartContainer">
          <div className="cartleft fleft mt-10 ml-10">
            {addresses.map((address) => (
              <p>{address.areaName}</p>
            ))}
            Shared object
            
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Address;
