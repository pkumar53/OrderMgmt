import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Medicines from './components/Medicines';
import Report from './components/Report';
import Cart from './components/Cart';
import User from './components/User';
import Notification from './components/Notification';
import Address from './components/Address';
import AddOrEditAddress from './components/AddOrEditAddress';
import Order from './components/Order';
import OrderDetail from './components/OrderDetail';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

const router = createBrowserRouter ([
  {
    path: "/",
    element: <App />
  },
  {
    path: "home",
    element: <App />
  },
  {
    path: "medicines",
    element: <Medicines />
  },
  {
    path: "order",
    element: <Order />
  },
  {
    path: "report",
    element: <Report />
  },
  {
    path: "notification",
    element: <Notification />
  },
  {
    path: "user",
    element: <User />
  },
  {
    path: "cart",
    element: <Cart />
  },
  {
    path: "address",
    element: <Address />
  },
  {
    path: "addOrEditAddress",
    element: <AddOrEditAddress />
  },
  {
    path: "orderDetail",
    element: <OrderDetail />
  }
])
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
