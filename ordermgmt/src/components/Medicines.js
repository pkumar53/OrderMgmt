import React, { useState, useEffect } from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import axios from "axios";
import "./common.css";
import "./Medicines.css";
import CartCount from './CartCount';

function Medicines() {
  const {cartCount, setCartCount} = CartCount();
  const [medicines, setMedicines] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState("medicines");

  useEffect(() => {
    const fetchMedicines = async () => {
      try {
        const response = await fetch("http://localhost:8083/products");
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setMedicines(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchMedicines();
  }, []);

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const addToCart = async (index) => {
    // call cart service to add the product to the cart
    setCartCount(cartCount + 1);
    try {
      const cartItem = {
        product: medicines[index],
        user: {
          userId: 1
        },
        quantity: 1
      }
      const response = await axios.post("http://localhost:8084/user/1/carts/addSelectedProduct", cartItem);
      console.log(response.data); // Success message
      return response.data;
    } catch (error) {
      setError(error.message);
    }
    alert(medicines[index].productName + " added to cart");
  };
  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">All Products</h3>
        {!loading ?
        <div className="product">
          {medicines.map((medicine, index) => (
            <div id={`prodbox${index + 1}`} className="prodbox">
              <div className="prodicon">
                <img src={medicine.imageUrl} alt={medicine.productName}></img>
              </div>
              <div className="prodname mt-20">
                <p className="name">{medicine.productName}</p>
                <p className="desc">
                  strip of {medicine.tabletsPerStrip} tablets
                </p>
                {/* <p className="desc">In Stocks {medicine.stocks}</p> */}
                <p className="desc">{medicine.brandName}</p>
              </div>
              <div className="prodinfo mt-10">
                <div className="fleft">
                  <p className="price">
                    MRP <strike>₹{(medicine.pricePerQty).toFixed(2)}</strike>{" "}
                    <div className="discount">{medicine.discountPercentage}% OFF</div>
                  </p>
                  <div className="fp">
                    ₹
                    {(medicine.discountedPrice).toFixed(2)}
                  </div>
                </div>
                <div className="fright">
                  <button
                    className="btn addbtn"
                    onClick={() => addToCart(index)}
                  >
                    Add
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div> : 
        <div className="product">
          <h1 style={{textAlign: "center"}} className="mt-40">Loading....</h1>
        </div>}
      </div>
      <Footer />
    </div>
  );
}

export default Medicines;
