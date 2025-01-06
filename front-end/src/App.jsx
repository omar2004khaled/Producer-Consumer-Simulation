import React, { useState } from 'react';
import Header from './Components/Header';
import SimulationArea from './Components/SimulationArea';

const App = () => {
  const [products, setProducts] = useState(0);

  return (
    <div style={{ display: "flex", flexDirection: "column" }}>
      <Header products={products} setProducts={setProducts} />
      <SimulationArea products={products}/>
    </div>
  );
};

export default App;
