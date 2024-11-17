import React, { useState } from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import medicines from "./medicines.json";
import "./common.css";
import "./Medicines.css";

function Medicines() {
    const addToCart = (index) => {
        alert(medicines[index].itemName + " added to cart");
    }
  const [activeTab, setActiveTab] = useState("medicines2");
  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} />
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">All Products</h3>
        <div className="product">
          {medicines.map((medicine, index) => (
            <div id={`prodbox${index+1}`} className="prodbox">
              <div className="prodicon">
                <img src={medicine.imageUrl} alt={medicine.itemName}></img>
              </div>
              <div className="prodname mt-20">
                <p className="name">{medicine.itemName}</p>
                <p className="desc">strip of {medicine.tabletsPerStrip} tablets</p>
                {/* <p className="desc">In Stocks {medicine.stocks}</p> */}
                <p className="desc">{medicine.company}</p>
              </div>
              <div className="prodinfo mt-10">
                <div className="fleft">
                  <p className="price">
                    MRP <strike>₹{medicine.mrp}</strike>{" "}
                    <div className="discount">{medicine.discount}% OFF</div>
                  </p>
                  <div className="fp">₹{medicine.finalPrice}</div>
                </div>
                <div className="fright">
                  <button className="btn addbtn" onClick={() => addToCart(index)}>Add</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Medicines;