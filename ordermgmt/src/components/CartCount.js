import { useState, useEffect } from "react";

const CartCount = () => {
  const [cartCount, setCartCount] = useState(0);

  useEffect(() => {
    const fetchCartCount = async () => {
      try {
        const response = await fetch("http://localhost:8084/cart/1/count");
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        setCartCount(data);
      } catch (err) {
      }
    };

    fetchCartCount();
  }, []);

  return { cartCount, setCartCount };
};

export default CartCount;
