import React, { useEffect, useState } from "react";
import Navbar from "./Navbar";
import CartCount from "./CartCount";
import Footer from "./Footer";

function Report() {
  const [activeTab, setActiveTab] = useState("report");
  const { cartCount } = CartCount();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    const abcd = "";
    setLoading(false);
  });
  
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
        <h3 className="mt-20 ml-20 mb-20">Report Page</h3>
        {loading ? 
          <div className="cartContainer">
            <h1 style={{ textAlign: "center" }} className="mt-40">Loading.....</h1>
          </div> :
          <>
            {cartCount > 0 ?
              <div className="cartContainer">
                <div className="cartleft fleft mt-10 ml-10">
                  {cartCount <= 0 ? 
                  <div className="order" >
                    <h1 style={{textAlign: "center"}} className="mt-40">No Reports data present yet.</h1>
                    <div className="mt-40">
                      <button className="btn placeOrderBtn mt-20" style={{width: "200px"}} onClick={() => window.location.href="medicines"}>Add Medicines</button>
                    </div>
                  </div> : 
                  <div className="order">
                  </div>}
                </div>
                <div className="cartright bill fleft mt-10 pad-20 ml-10"></div>
              </div>
            : <div className="cartContainer">
                <h1 style={{textAlign: "center"}} className="mt-40">No Reports generated yet.</h1>
                <div className="mt-40">
                  <button className="btn placeOrderBtn mt-20" style={{width: "200px"}} onClick={() => window.location.href="medicines"}>Add Medicines</button>
                </div>
              </div>
            }
          </>
        }
      </div>
      <Footer />
    </div>
  );
}

export default Report;
