import React from 'react';
import Dots from './Dots';

const Machine = ({ data }) => {
  return (
    <>
        <Dots id={data.id} height={80} width={80} />
        <div style={{
        width: 80,
        height: 80,
        borderRadius: '50%',
        background: "lightblue",
        alignItems: 'center',
        justifyContent: 'center',
        display: 'flex',
        border: '1px solid',
        textAlign: "center",
        boxShadow: `0 0 20px ${data.color}`
        }}>
        {data.label}
        </div>
    </>
  );
};

export default Machine;