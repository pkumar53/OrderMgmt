import React from "react";
import "./common.css";
import "./Container.css";
import Slider from "./Slider";

function Container() {
  const images = [
    { key: 0, name: "rectangle.png" },
    { key: 1, name: "rectangle2.png" },
    { key: 2, name: "rectangle3.png" },
  ];

  return (
    <div className="container">
      <div className="offerBox mt-20">
        <Slider images={images}/>
      </div>
      <div className="aboutBox mt-20">
        <h1>About Us</h1>
        <p className="infobox mt-20 fleft">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum
          dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor
          incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
          quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
          commodo consequat. Duis aute irure dolor in reprehenderit in voluptate
          velit esse cillum dolore eu fugiat nulla pariatur.
        </p>
        <img
          className="infoicon fright"
          src="favicon.ico"
          alt="mypic"
        ></img>
      </div>
    </div>
  );
}

export default Container;
