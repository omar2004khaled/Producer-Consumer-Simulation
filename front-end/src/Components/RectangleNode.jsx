import React from 'react';

const RectangleNode = ({ data }) => {
  return (
    <div style={{
      width: 100,
      height: 50,
      backgroundColor: "yellow",
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      border: '1px solid',
      borderRadius: "5px",
      textAlign: "center"
    }}>
      {data.label}
    </div>
  );
};

export default RectangleNode;