import React, {useState} from 'react';
import Navbar from './Navbar';
import CartCount from './CartCount';

function Report() {
  const [activeTab, setActiveTab] = useState('report');
  const {cartCount, setCartCount} = CartCount();
  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      Report page
    </div>
  );
}

export default Report;
