import React, {useState} from 'react'
import Navbar from './Navbar'
import Footer from './Footer'
import orders from './orders.json'
import CartCount from './CartCount';

function Order(props) {
  const [activeTab, setActiveTab] = useState('order');
  const {cartCount, setCartCount} = CartCount();
  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab} cartCount={cartCount}/>
      <div className="container mb-20">
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
                  <td>{order.orderId}</td>
                  <td>{order.orderDate}</td>
                  <td>{order.status}</td>
                  <td>{order.totalAmount}</td>
                  <td>{order.shippingAddress}</td>
                  <td>{order.customerName}</td>
                  {/* <td>{order.items}</td> */}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <Footer />
    </div>
  )
}

export default Order
