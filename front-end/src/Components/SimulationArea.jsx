import React from 'react';
import Shape from './Shape';
import '../Style/simulationArea.css'

const SimulationArea = ({ shapes, setShapes }) => {
  const createShape = (type) => {
    const newShape = { id: Date.now(), type };
    setShapes([...shapes, newShape]);
  };

  return (
    <div className="simulation-area" onClick={(e) => createShape('machine')}>
      {shapes.map((shape) => (
        <Shape key={shape.id} type={shape.type} />
      ))}
    </div>
  );
};

export default SimulationArea;