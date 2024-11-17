import React, { useState } from "react";
import menus from "./menus.json";

function CommonNavBar() {
  const [query, setQuery] = useState("");
  const goToPage = (menu) => {
    window.location.href = menu;
  };
  return (
    <nav className="container">
      <div className="prodlogo fleft">
        <img className="fleft" src="./favicon.ico" alt=""></img>
        <p className="ml-20 aligncenter fleft">Medicine Stocks</p>
      </div>
      <div className="searchbar aligncenter fleft">
        <input
          className="input"
          placeholder="Search"
          onChange={(e) => setQuery(e.target.value)}
          value={query}
        ></input>
        {/* <button className="btn ml-10">Search</button> */}
      </div>
      <div className="navbar-right fright">
        {menus.map((menu) => (
          <div className="fleft ml-20" onClick={() => goToPage(menu.value)}>
            <img src={menu.url} alt={menu.label}></img>
          </div>
        ))}
      </div>
    </nav>
  );
}

export default CommonNavBar;
