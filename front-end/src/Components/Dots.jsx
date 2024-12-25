import React from 'react';
import { Handle, Position } from '@xyflow/react';

export default function Dots( id ) {
  return (
    <>
      <Handle id={`${id}-1OUT`} type="source" position={Position.Left} style={{
        height: '90%',
        width: '20px',
        opacity: '0',
        borderRadius: "0"
      }}/>
      <Handle id={`${id}-1IN`} type="target" position={Position.Left} style={{
        height: '90%',
        width: '20px',
        opacity: '0',
        borderRadius: "0"
      }}/>

      <Handle id={`${id}-2OUT`} type="source" position={Position.Right} style={{
        height: '90%',
        width: '20px',
        opacity: '0',
        borderRadius: "0"
      }}/>
      <Handle id={`${id}-2IN`} type="target" position={Position.Right} style={{
        height: '90%',
        width: '20px',
        opacity: '0',
        borderRadius: "0"
      }}/>
    </>
  );
}