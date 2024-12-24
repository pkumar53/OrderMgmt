import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import CartCount from "./CartCount";
import Navbar from "./Navbar";
import Footer from "./Footer";

function OrderDetail() {
  const [activeTab, setActiveTab] = useState("order");
  const { cartCount, setCartCount } = CartCount();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const orderDetailId = searchParams.get("orderDetailId");

  useEffect(() => {
    setLoading(false);
  }, [orderDetailId]);

  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount} />
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">Orders</h3>
        {loading ? (
          <div className="cartContainer">
            <h1 style={{ textAlign: "center" }} className="mt-40">Loading.....</h1>
          </div>
        ) : (
          <>
            {cartCount <= 0 ? (
              <div className="cartContainer mt-20">
                TBD
                <br />
                Order details is to be fetched for order detail id {orderDetailId}
              </div>
            ) : (
              <div className="cartContainer">
                <h1 style={{ textAlign: "center" }} className="mt-40">No Orders present yet.</h1>
                <div className="mt-40">
                  <button className="btn placeOrderBtn mt-20" style={{ width: "200px" }} onClick={() => (window.location.href = "medicines")}>Add Medicines</button>
                </div>
              </div>
            )}
          </>
        )}
      </div>
      <Footer />
    </div>
  );
}

export default OrderDetail;
