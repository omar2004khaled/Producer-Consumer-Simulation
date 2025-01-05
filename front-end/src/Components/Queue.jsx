import React from 'react';
import Dots from './Dots';

const Queue = ({ data }) => {
  return (
    <>
      <Dots id={data.id} height={80} width={80} />
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
        {data.noOfProducts > 0 &&
        (<><br></br> ({data.noOfProducts})</>)}
      </div>
    </>
  );
};

export default Queue;