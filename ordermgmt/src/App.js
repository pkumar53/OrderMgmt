import Navbar from "./components/Navbar";
import Container from "./components/Container";
import Footer from "./components/Footer";
import { useState } from "react";

function App() {
  const [activeTab, setActiveTab] = useState('home');
  
  return (
    <>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab}/>
      <Container />
      <Footer />
    </>
  );
}

export default App;
