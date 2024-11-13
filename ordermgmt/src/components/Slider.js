import React, { useState } from "react";
import "./Slider.css";

function Slider(props) {
  const images = props.images;
  const len = images.length - 1;

  const [activeIndex, setActiveIndex] = useState(1);

  return (
    <div className="slider-container">
      {images.map((slide, index) => (
        <div key={index}
          className={index === activeIndex ? "slides active" : "slides active inactive"}>
          <img className="slide-image" src={slide.name} alt="" />
        </div>
      ))}
      <div className="arrows">
        <span
          className="prev"
          onClick={() =>
            setActiveIndex(activeIndex < 1 ? len : activeIndex - 1)
          }>
          &#10094;
        </span>
        <span className="next"
          onClick={() =>
            setActiveIndex(activeIndex === len ? 0 : activeIndex + 1)
          }>
          &#10095;
        </span>
      </div>
      <div className="all-dots">
        {images.map((slide, ind) => (
          <span
            key={ind}
            className={`${activeIndex === ind ? "dot active-dot" : "dot"}`}
            onClick={() => setActiveIndex(ind)}>
          </span>
        ))}
      </div>
    </div>
  );
}

export default Slider;
