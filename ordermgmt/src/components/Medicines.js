import React, { useState } from "react";
import Navbar from "./Navbar";
import Footer from "./Footer";
import medicines from "./medicines.json";
import "./Medicine.css";
import "./common.css";

function Medicines() {
  const [activeTab, setActiveTab] = useState("medicines");
  const [qty, setQty] = useState(0);

  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} />

      <div className="container mb-20">
        <div className="mt-20">
          <table className="table">
            <thead>
              <tr>
                <th>Sl No</th>
                <th>Item Name</th>
                <th>Company</th>
                <th>Stocks</th>
                <th>Discount</th>
                <th>Puchased Price</th>
                <th>MRP</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {medicines.map((medicine, index) => (
                <tr key={medicine.slNo}>
                  <td>{medicine.slNo}</td>
                  <td>{medicine.itemName}</td>
                  <td>{medicine.company}</td>
                  <td>{medicine.stocks}</td>
                  <td>{medicine.discount}</td>
                  <td>{medicine.purchasedPrice}</td>
                  <td>{medicine.mrp}</td>
                  <td>
                    <div className="qty-inp">
                      <button onClick={() => setQty(qty - 1)}>-</button>
                      <input value={qty} onChange={(e) => setQty(Number(e.target.value))} />
                      <button onClick={() => setQty(qty + 1)}>+</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Medicines;
