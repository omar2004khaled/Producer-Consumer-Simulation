import React from 'react';
import '../Style/Header.css';
import logo from "/src/assets/prosumers-and-customer-service.jpg";

const Header = ({ products, setProducts }) => {
  const handleInputChange = (e) => {
    const value = parseInt(e.target.value, 10);
    setProducts(Number.isNaN(value) ? 0 : value); // Default to 0 for invalid input
  };

  return (
    <header>
      <div className="header-controls">
        <div className="header-left">
          <img src={logo} alt="Logo" className="logo" />
          <h2>Producer-Consumer Simulation</h2>
        </div>
        <div className="header-center">
          <p>Number of Products: &nbsp;</p>
          <input
            type="text"
            value={products}
            onChange={handleInputChange}
            className="digital-input"
            pattern="\d*" // This pattern ensures only digits are accepted
          />
        </div>
      </div>
    </header>
  );
};

export default Header;