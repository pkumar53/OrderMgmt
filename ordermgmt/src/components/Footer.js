import React from "react";
import "./common.css";
import "./Footer.css";

class Footer extends React.Component {
  state = {};
  render() {
    return (
      <div className="footer">
        <div className="container">
          <div className="col-3 mt-20">
            <img
              className="footerlogo"
              src="./favicon.ico"
              alt="Medicine Stocks"
            ></img>
          </div>
          <div className="col-3 mt-20 footertext">
            All Copyrights reserved
            <br />
            Medical Stocks
            <br/>
            <br/>
            <br/>
            <br/>
            Address: Bellandur, Bangalore, 560103
            <br/>
            Contact Us: +91 000 000 0000 / Email Us: xyz@abc.com
          </div>
          <div className="col-3 mt-20 findUs">
            <img className="facebook ml-20" src="./fb.png" alt="facebook" />
            <img className="twitter ml-20" src="./twitter.png" alt="twitter" />
            <img className="insta ml-20" src="./insta.png" alt="instagram"/>
            <img className="youtube ml-20" src="./youtube.png" alt="youtube" />
          </div>
        </div>
      </div>
    );
  }
}

export default Footer;
