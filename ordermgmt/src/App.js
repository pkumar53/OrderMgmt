import Navbar from "./components/Navbar";
import Container from "./components/Container";
import Footer from "./components/Footer";
import { useState } from "react";
import CartCount from "./components/CartCount";
function App() {
  const [activeTab, setActiveTab] = useState('home');
  const {cartCount, setCartCount} = CartCount();
  return (
    <>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount} setCartCount={setCartCount}/>
      <Container />
      <Footer />
    </>
  );
}

export default App;
