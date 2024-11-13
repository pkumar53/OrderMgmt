import React, {useState} from "react";
import "./Navbar.css";
import "./common.css";
import navMenus from "./navMenus.json";
import menus from "./menus.json";

function Navbar(props) {
  const [query, setQuery] = useState("");
  const [deboucedQuery, setDebouncedQuery] = useState(query);


  const navigate = (section) => {
    props.setActiveTab(section);
    goToPage(section);
  };
  const goToPage = (menu) => {
    window.location.href = menu;
  };

  const filterData = (e) => {
    let input = e.target.value;
    console.log(input);
    setTimeout()
  }
  return (
    <div className="navbar">
      <nav className="container">
        <div className="prodlogo fleft">
          <img className="ml-20 fleft" src="./favicon.ico" alt=""></img>
          <p className="ml-20 aligncenter fleft">Medicine Stocks</p>
        </div>
        <div className="searchbar  aligncenter fleft">
          <input className="input" placeholder="Search" onChange={(e) => setQuery(e.target.value)} value={query}></input>
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
      <div className="subnavbar">
        <ul>
          {navMenus.map((navMenu) => (
            <li
              className={`fleft aligncenter ${
                props.activeTab === navMenu.value ? "activeTab" : ""
              }`}
              onClick={() => navigate(navMenu.value)}
            >
              {navMenu.label}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Navbar;
