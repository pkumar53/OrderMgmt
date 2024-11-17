import React from "react";
import "./Navbar.css";
import "./common.css";
import navMenus from "./navMenus.json";
import CommonNavBar from './CommonNavBar';

function Navbar(props) {
  const navigate = (section) => {
    props.setActiveTab(section);
    goToPage(section);
  };
  const goToPage = (menu) => {
    window.location.href = menu;
  };

  return (
    <>
      <CommonNavBar />
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
    </>
  );
}

export default Navbar;
