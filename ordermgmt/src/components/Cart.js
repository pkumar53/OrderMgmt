import React, { useState, useEffect } from "react";
import Footer from "./Footer";
import "./common.css";
import "./Cart.css";
import axios from "axios";
import CartCount from "./CartCount";
import DeliveryAddress from "./DeliveryAddress";
import Navbar from "./Navbar";
import { useLocation } from "react-router-dom";

function Cart(props) {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const deliveryAddressId = searchParams.get("addressId");
  const [activeTab, setActiveTab] = useState("user");
  const {cartCount, setCartCount} = CartCount();
  const [filteredItems, setFilteredItems] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        const response = await fetch("http://localhost:8084/user/1/carts");
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

  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const decrQty = async (key) => {
    setCartCount(cartCount - 1);
    const updatedItems = filteredItems.map((item) =>
      item.cartId === key ? {
        ...item,
        quantity: item.quantity - 1,
        discount: item.product.discount * (item.quantity - 1),
        discountedPrice: item.product.discountedPrice * (item.quantity - 1),
        price: item.product.pricePerQty * (item.quantity - 1)
      } : item
    );

    // Update the local state first
    setFilteredItems(updatedItems);

    // Find the updated item to send to the backend
    const updatedItem = updatedItems.find((item) => item.cartId === key);

    if (updatedItem.quantity <= 0) {
      try {
        // If quantity is 0 or less, delete the cart item
        const response = await axios.delete("http://localhost:8084/user/1/carts", {data : updatedItem});
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
        const response = await axios.post("http://localhost:8084/user/1/carts/update", updatedItem);
        console.log("Decrement response:", response.data);
      } catch (error) {
        console.error("Error decrementing quantity:", error.response || error.message);
        // Optionally, revert the state update on error
        setFilteredItems((prevItems) =>
          prevItems.map((item) =>
            item.cartId === key ? {
              ...item, 
              quantity: item.quantity + 1,
              discount: item.product.discount * (item.quantity + 1),
              discountedPrice: item.product.discountedPrice * (item.quantity + 1),
              price: item.product.pricePerQty * (item.quantity + 1)
            } : item
          )
        );
      }
    }
  };

  const incrQty = async (key) => {
    setCartCount(cartCount + 1);
    const updatedItems = filteredItems.map((item) =>
      item.cartId === key ? {
        ...item, 
        quantity: item.quantity + 1,
        discount: item.product.discount * (item.quantity + 1),
        discountedPrice: item.product.discountedPrice * (item.quantity + 1),
        price: item.product.pricePerQty * (item.quantity + 1)
      } : item
    );

    // Update the local state first
    setFilteredItems(updatedItems);

    // Find the updated item to send to the backend
    const updatedItem = updatedItems.find((item) => item.cartId === key);
    
    try {
      // Call the backend API to update the quantity
      const response = await axios.post("http://localhost:8084/user/1/carts/update", updatedItem);
      console.log("Increment response:", response.data);
    } catch (error) {
      console.error("Error incrementing quantity:", error.response || error.message);
      // Optionally, revert the state update on error
      setFilteredItems((prevItems) =>
        prevItems.map((item) =>
          item.cartId === key ? {
            ...item,
            quantity: item.quantity - 1,
            discount: item.product.discount * (item.quantity - 1),
            discountedPrice: item.product.discountedPrice * (item.quantity - 1),
            price: item.product.pricePerQty * (item.quantity - 1)
          } : item
        )
      );
    }
  };

  const calculateTotalPrice = () => {
    return filteredItems.reduce((total, item) => 
      total + item.price, 0
    );
  };
  const calculateTotalDiscountPrice = () => {
    return filteredItems.reduce((totalDiscount, item) => 
      totalDiscount + item.discount, 0
    );
  };
  const packagingCharge = 4;
  const shippingFee = 10;

  const placeOrder = async () => {
    if (deliveryAddressId) {
        try {
          const cartDetails = {
            cartItems: filteredItems,
            shippingAddressId: deliveryAddressId,
            userId: 1,
            shippingFee: shippingFee,
            packagingCharge: packagingCharge,
            totalAmount: (calculateTotalPrice() - calculateTotalDiscountPrice() + packagingCharge + shippingFee).toFixed(2),
            totalDiscount: calculateTotalDiscountPrice().toFixed(2)
          }
          const response = await axios.post("http://localhost:8085/user/1/orders/create", cartDetails);
          if (response.status === 201) {
            alert("Order placed successfully");
            window.location.href='order';
          } else {
            alert("Error while placing order");
          }
        } catch(err) {
          //may be stock  not available
          //show on UI which products stock are not available
        }
      } else {
        window.location.href='address';
      }
  };

  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      <div className="container mb-20">
        <h3 className="mt-20 ml-20 mb-20">Cart</h3>
        {loading ? 
          <div className="cartContainer">
            <h1 style={{textAlign: "center"}} className="mt-40">Loading....</h1>
          </div> :
          <>
            {filteredItems.length > 0 ?
            <div className="cartContainer">
              <div className="cartleft fleft mt-10 ml-10">
                <div className="order">
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
                            ₹ {(cartItem.discountedPrice).toFixed(2)}{" "}
                            <strike>
                              ₹{cartItem.price}
                            </strike>{" "}
                            <div className="discount">
                              {cartItem.discountPercentage}% OFF
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
                  <p className="desc">
                    Estimated shipping fee{" "}
                    <div className="fright">₹ {shippingFee}</div>
                  </p>
                  <p className="discount">
                    Total discount{" "}
                    <div className="fright">
                      - ₹ {calculateTotalDiscountPrice().toFixed(2)}
                    </div>
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
                  <button className="btn placeOrderBtn mt-20" onClick={() => placeOrder()}>Place Order</button>
                </div>
                {deliveryAddressId > 0 ?
                  <DeliveryAddress shippingAddressId={deliveryAddressId}/>
                : (<></>)}
              </div>
            </div> :
            <div className="cartContainer">
              <h1 style={{textAlign: "center"}} className="mt-40">No Medicines added yet.</h1>
              <div className="mt-40">
                <button className="btn placeOrderBtn mt-20" style={{width: "200px"}} onClick={() => window.location.href="medicines"}>Add Medicines</button>
              </div>
            </div>
            }
          </>
        }
      </div>
      <Footer />
    </div>
  );
}

export default Cart;
