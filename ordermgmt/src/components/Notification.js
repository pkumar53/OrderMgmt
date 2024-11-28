import React, { useState } from 'react'
import CommonNavBar from './CommonNavBar'
import CartCount from './CartCount'
import Navbar from './Navbar';

function Notification() {
  const [activeTab, setActiveTab] = useState("user");
  const {cartCount, setCartCount} = CartCount();
  return (
    <>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      </>
  )
}

export default Notification
