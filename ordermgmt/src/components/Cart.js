import React, { useState, useEffect } from "react";
import CommonNavBar from "./CommonNavBar";
import Footer from "./Footer";
import "./common.css";
import "./Cart.css";
import axios from "axios";

function Cart() {
  const [filteredItems, setFilteredItems] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        const response = await fetch("http://localhost:8084/cart/1");
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setFilteredItems(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCartItems();
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const decrQty = async (key) => {
    const updatedItems = filteredItems.map((item) =>
      item.cartId === key ? { ...item, quantity: item.quantity - 1 } : item
    );

    // Update the local state first
    setFilteredItems(updatedItems);

    // Find the updated item to send to the backend
    const updatedItem = updatedItems.find((item) => item.cartId === key);

    if (updatedItem.quantity <= 0) {
      try {
        // If quantity is 0 or less, delete the cart item
        const response = await axios.delete("http://localhost:8084/cart", {data : updatedItem});
        console.log("Item deleted successfully:", response.data);

        // Update the UI by removing the deleted item from the state
        setFilteredItems((prevItems) =>
          prevItems.filter((item) => item.cartId !== key)
        );
      } catch (error) {
        console.error("Error deleting item:", error.response || error.message);
      }
    } else {
      try {
        // Call the backend API to update the quantity
        const response = await axios.post("http://localhost:8084/cart", updatedItem);
        console.log("Increment response:", response.data);
      } catch (error) {
        console.error("Error incrementing quantity:", error.response || error.message);
        // Optionally, revert the state update on error
        setFilteredItems((prevItems) =>
          prevItems.map((item) =>
            item.cartId === key ? { ...item, quantity: item.quantity + 1 } : item
          )
        );
      }
    }
  };

  const incrQty = async (key) => {
    const updatedItems = filteredItems.map((item) =>
      item.cartId === key ? { ...item, quantity: item.quantity + 1 } : item
    );

    // Update the local state first
    setFilteredItems(updatedItems);

    // Find the updated item to send to the backend
    const updatedItem = updatedItems.find((item) => item.cartId === key);
    
    try {
      // Call the backend API to update the quantity
      const response = await axios.post("http://localhost:8084/cart", updatedItem);
      console.log("Increment response:", response.data);
    } catch (error) {
      console.error("Error incrementing quantity:", error.response || error.message);
      // Optionally, revert the state update on error
      setFilteredItems((prevItems) =>
        prevItems.map((item) =>
          item.cartId === key ? { ...item, quantity: item.quantity - 1 } : item
        )
      );
    }
  };

  const calculateTotalPrice = () => {
    return filteredItems.reduce(
      (total, item) =>
        total +
        item.quantity * (item.product.pricePerQty - (item.product.pricePerQty * (item.product.discount / 100))),
      0
    );
  };
  const calculateTotalDiscountPrice = () => {
    return filteredItems.reduce(
      (totalDiscount, item) =>
        totalDiscount +
        item.quantity * ((item.product.pricePerQty * item.product.discount) / 100),
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
                      src={cartItem.product.imageUrl}
                      alt={cartItem.product.productName}
                    ></img>
                  </div>
                  <div className="cartprodname fleft mt-20">
                    <p className="cartname">{cartItem.product.productName}</p>
                    <p className="desc">
                      strip of {cartItem.product.tabletsPerStrip} tablets
                    </p>
                    {/* <p className="desc">In Stocks {medicine.stocks}</p> */}
                    <p className="desc">{cartItem.product.brandName}</p>
                  </div>
                  <div className="fright mt-20">
                    <div>
                      <div className="cartprice">
                        ₹ {((cartItem.product.pricePerQty - (cartItem.product.pricePerQty * cartItem.product.discount) / 100) * cartItem.quantity).toFixed(2)}{" "}
                        <strike>
                          ₹{cartItem.product.pricePerQty * cartItem.quantity}
                        </strike>{" "}
                        <div className="discount">
                          {cartItem.product.discount}% OFF
                        </div>
                      </div>
                      <div className="cartbtn fright mt-20">
                        <button onClick={() => decrQty(cartItem.cartId)}>
                          {" "}
                          -{" "}
                        </button>
                        <div> {cartItem.quantity} </div>
                        <button onClick={() => incrQty(cartItem.cartId)}>
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
                  ₹ {calculateTotalPrice().toFixed(2)}
                </div>
              </p>
              <p className="desc">
                Packaging charge{" "}
                <div className="fright">₹ {packagingCharge}</div>
              </p>
              <p className="discount">
                Total discount{" "}
                <div className="fright">
                  - ₹ {calculateTotalDiscountPrice().toFixed(2)}
                </div>
              </p>
              <p className="discount">
                Estimated shipping fee{" "}
                <div className="fright">₹ {shippingFee}</div>
              </p>
            </div>
            <div className="total mt-20">
              <p className="desc mt-10">
                To be paid{" "}
                <div className="fright">
                  ₹{" "}
                  {(calculateTotalPrice() -
                    calculateTotalDiscountPrice() +
                    packagingCharge +
                    shippingFee).toFixed(2)}
                </div>
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
