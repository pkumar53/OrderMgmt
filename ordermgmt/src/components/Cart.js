import React, { useState } from "react";
import CommonNavBar from "./CommonNavBar";
import Footer from "./Footer";
import "./common.css";
import "./Cart.css";
import cartItemsJson from "./cartItems.json";

function Cart() {
  const [cartItems, setCartItems] = useState(cartItemsJson);
  const filteredItems = cartItems.filter(
    (cartItem) => cartItem.medicine.addedToCart
  );
  const decrQty = (key) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.key === key ? { ...item, quantity: item.quantity - 1 } : item
      )
    );
  };
  const incrQty = (key) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.key === key ? { ...item, quantity: item.quantity + 1 } : item
      )
    );
  };
  const calculateTotalPrice = () => {
    return filteredItems.reduce(
      (total, item) => total + item.quantity * item.medicine.finalPrice,
      0
    );
  };
  const calculateTotalDiscountPrice = () => {
    return filteredItems.reduce(
      (totalDiscount, item) =>
        totalDiscount + item.quantity * (item.medicine.mrp - item.medicine.finalPrice),
      0
    );
  };
  const packagingCharge = 4;
  const shippingFee = 10;
  return (
    <div>
      <CommonNavBar />
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">Cart</h3>
        <div className="cartContainer">
          <div className="cartleft fleft mt-10 ml-10">
            <div className="order">
              <h4>Order #12123123</h4>
              {filteredItems.map((cartItem, index) => (
                <div className="cartproduct mt-20 pad-20">
                  <div className="cartprodicon fleft mt-20">
                    <img
                      src={cartItem.medicine.imageUrl}
                      alt={cartItem.medicine.itemName}
                    ></img>
                  </div>
                  <div className="cartprodname fleft mt-20">
                    <p className="cartname">{cartItem.medicine.itemName}</p>
                    <p className="desc">
                      strip of {cartItem.medicine.tabletsPerStrip} tablets
                    </p>
                    {/* <p className="desc">In Stocks {medicine.stocks}</p> */}
                    <p className="desc">{cartItem.medicine.company}</p>
                  </div>
                  <div className="fright mt-20">
                    <div>
                      <div className="cartprice">
                        ₹{cartItem.medicine.finalPrice * cartItem.quantity}{" "}
                        <strike>
                          ₹{cartItem.medicine.mrp * cartItem.quantity}
                        </strike>{" "}
                        <div className="discount">
                          {cartItem.medicine.discount}% OFF
                        </div>
                      </div>
                      <div className="cartbtn mt-20">
                        <button onClick={() => decrQty(cartItem.key)}>
                          {" "}
                          -{" "}
                        </button>
                        <div> {cartItem.quantity} </div>
                        <button onClick={() => incrQty(cartItem.key)}>
                          {" "}
                          +{" "}
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
          <div className="cartright bill fleft mt-10 pad-20 ml-10">
            <h4>Bill Summary</h4>
            <div className="summary mt-40">
              <p className="desc">
                Item total{" "}
                <div className="fright">
                  $ {calculateTotalPrice().toFixed(2)}
                </div>
              </p>
              <p className="desc">
                Packaging charge <div className="fright">₹ {packagingCharge}</div>
              </p>
              <p className="discount">
                Total discount <div className="fright">- ₹ {calculateTotalDiscountPrice()}</div>
              </p>
              <p className="discount">
                Estimated shipping fee <div className="fright">₹ {shippingFee}</div>
              </p>
            </div>
            <div className="total mt-20">
              <p className="desc mt-10">
                To be paid <div className="fright">₹ {calculateTotalPrice() - calculateTotalDiscountPrice() + packagingCharge + shippingFee}</div>
              </p>
            </div>
            <div className="mt-40">
              <button className="btn placeOrderBtn mt-20">Place Order</button>
            </div>
            <div className="delivery mt-40">
              <h4>Delivery Address</h4>
              <div className="mt-20">
                <p className="desc">G3 Ground Floor Lake Vista Bellandur</p>
                <p className="desc">Bangalore, Karnataka</p>
                <p className="desc">560103</p>
              </div>
              <button className="btn edtAdd">Edit</button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Cart;
