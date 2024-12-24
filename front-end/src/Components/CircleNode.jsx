import React from 'react';

const CircleNode = ({ data }) => {
  return (
    <div style={{
      width: 80,
      height: 80,
      borderRadius: '50%',
      background: "lightblue",
      alignItems: 'center',
      justifyContent: 'center',
      display: 'flex',
      border: '1px solid',
      textAlign: "center"
    }}>
      {data.label}
    </div>
  );
};

export default CircleNode;