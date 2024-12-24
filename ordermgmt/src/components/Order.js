import React, { useEffect, useState} from 'react'
import './Order.css'
import Navbar from './Navbar'
import Footer from './Footer'
import CartCount from './CartCount';
import { format } from 'date-fns';
import { useNavigate } from "react-router-dom";

function Order(props) {
  const [activeTab, setActiveTab] = useState('order');
  const {cartCount, setCartCount} = CartCount();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();

  const formatDate = (date) => {
    return format(new Date(date), 'dd-MM-yyyy HH:mm');
  };

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await fetch("http://localhost:8085/user/1/orders");
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setOrders(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, []);
  
  if (error) {
    return <p style={{ color: "red" }}>Error: {error}</p>;
  }

  const fetchOrderStatus = (status, orderDate, deliveryDate) => {
    if (status === "PLACED") {
      return <><p className='date'>Placed on {formatDate(orderDate)}</p><p>Your order has been placed.</p></>;
    } else if (status === "COMPLETED") {
      return <><p className='date'>Delivered on {deliveryDate}</p><p>Your item has been delivered.</p></>;
    }
  };

  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      <div className="container mb-20">
      <h3 className="mt-20 ml-20 mb-20">Orders</h3>
        {loading ? 
          <div className="cartContainer">
            <h1 style={{ textAlign: "center" }} className="mt-40">Loading.....</h1>
          </div> :
          <>
            {orders.length > 0 ?
              <div className="cartContainer mt-20">
                <div className='cartLeftContainer fleft'>
                  <h4 className='mt-20 mb-20'>Filter</h4>
                  <div className='filterCriteria mb-20'>
                    <p>ORDER STATUS</p>
                    <div>
                      <input type='checkbox'></input>
                      <p>On the way</p>
                    </div>
                    <div>
                      <input type='checkbox'></input>
                      <p>Delivered</p>
                    </div>
                    <div>
                      <input type='checkbox'></input>
                      <p>Cancelled</p>
                    </div>
                    <div>
                      <input type='checkbox'></input>
                      <p>Returned</p>
                    </div>
                  </div>
                  <div className='filterCriteria'>
                    <p>ORDER TIME</p>
                    <div>
                      <input type='checkbox'></input>
                      <p>Last 30 days</p>
                    </div>
                    <div>
                      <input type='checkbox'></input>
                      <p>2023</p>
                    </div>
                    <div>
                      <input type='checkbox'></input>
                      <p>2022</p>
                    </div>
                    <div>
                      <input type='checkbox'></input>
                      <p>Older</p>
                    </div>
                  </div>
                </div>
                <div className='cartRightContainer fleft'>
                  {orders.map((order, index) => 
                    order.orderDetailReqDTOs.map((orderDet, ind) => 
                      (
                        <div className='orderDiv' onClick={() => navigate(`/orderDetail?orderDetailId=${orderDet.orderDetailId}`)}>
                          <p className='fontsize12 ml-40 mb-20'>Order Reference: {order.orderReferenceNumber}</p>
                          <div className='imageDiv fleft'>
                            <img src='./favIcon.ico' />
                          </div>
                          <div className='orderInfo fleft'>
                            <p className='prodName'>{orderDet.product.productName}</p>
                            <p className='prodDetails'>{orderDet.product.brandName}</p>
                            <p className='prodDetails'>Quantity {orderDet.quantity}</p>
                          </div>
                          <div className='prodPrice fleft'><p>₹{orderDet.discountedPrice.toFixed(2)}</p></div>
                          <div className='deliveryInfo fright'>
                            {fetchOrderStatus(orderDet.status, order.orderDate, orderDet.deliveryDate)}
                          </div>
                      </div>
                      )
                    )
                  )}
                </div>
              </div>
            : <div className="cartContainer">
                <h1 style={{textAlign: "center"}} className="mt-40">No Orders present yet.</h1>
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
  )
}

export default Order
