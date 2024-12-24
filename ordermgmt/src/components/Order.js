import React, { useEffect, useState} from 'react'
import Navbar from './Navbar'
import Footer from './Footer'
import orders from './orders.json'
import CartCount from './CartCount';

function Order(props) {
  const [activeTab, setActiveTab] = useState('order');
  const {cartCount, setCartCount} = CartCount();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [orders, setOrders] = useState([]);
  
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
              <div className="mt-20">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Order Id</th>
                      <th>Order Date</th>
                      <th>Status</th>
                      <th>Amount</th>
                      <th>Address</th>
                      <th>Customer</th>
                    </tr>
                  </thead>
                  <tbody>
                    {orders.map((order, index) => (
                      <tr key={index + 1}>
                        <td>{order.orderReferenceNumber}</td>
                        <td>{order.orderDate}</td>
                        <td>{order.orderStatus}</td>
                        <td>{order.totalAmountForOrder}</td>
                        <td>{order.address.areaName}, {order.address.addressDetail}</td>
                        <td>{order.user.userFullName}</td>
                        {/* <td>{order.items}</td> */}
                      </tr>
                    ))}
                  </tbody>
                </table>
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
