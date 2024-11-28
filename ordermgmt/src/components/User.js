import React, { useState } from 'react'
import CartCount from './CartCount'
import Navbar from './Navbar';
import Footer from './Footer';

function User() {
  const [activeTab, setActiveTab] = useState("user");
  const {cartCount} = CartCount();
  return (
    <>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">User Profile</h3>
        <div className="cartContainer">
          <div className="cartleft fleft mt-10 ml-10">
            {cartCount <= 0 ? 
            <div className="order" >
              <h1 style={{textAlign: "center"}} className="mt-40">No User Profile data present yet.</h1>
              <div className="mt-40">
                <button className="btn placeOrderBtn mt-20" style={{width: "200px"}} onClick={() => window.location.href="medicines"}>Add Medicines</button>
              </div>
            </div> : 
            <div className="order">
            </div>}
          </div>
          <div className="cartright bill fleft mt-10 pad-20 ml-10"></div>
        </div>
      </div>
      <Footer />
      </>
  )
}

export default User
