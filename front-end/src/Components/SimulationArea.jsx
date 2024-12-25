import '../Style/simulationArea.css';
import React, { useCallback } from 'react';
import '@xyflow/react/dist/style.css';

import {
  ReactFlow,
  Controls,
  Background,
  useNodesState,
  useEdgesState,
  addEdge,
  ReactFlowProvider,
  SmoothStepEdge,
  MarkerType,
} from '@xyflow/react';

import CircleNode from './CircleNode';
import RectangleNode from './RectangleNode';
import { Arrow } from './Arrow';

const nodeTypes = {
  circle: CircleNode,
  rectangle: RectangleNode,
};

const SimulationArea = () => {
  const initialNodes = [
    { id: '1', type: 'circle', position: { x: 0, y: 0 }, data: { label: 'Circle Node' } },
    { id: '2', type: 'rectangle', position: { x: 0, y: 100 }, data: { label: 'Rectangle Node' } },
  ];
  const initialEdges = [
    {
      id: 'e1-2',
      source: '1',
      target: '2',
      ...Arrow,
    },
  ];
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

  const onConnect = useCallback(
    (params) => setEdges((eds) => addEdge({ ...params, ...Arrow }, eds)),
    [setEdges],
  );

  const addNode = () => {
    const newNode = {
      id: (nodes.length + 1).toString(),
      type: 'circle', // or 'rectangle' depending on what you want to add
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: { label: `Node ${nodes.length + 1}` },
    };
    setNodes((nds) => [...nds, newNode]);
  };

  return (
    <div className='react-flow-container'>
      <button onClick={addNode}>Add Node</button>
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        onConnect={onConnect}
        nodeTypes={nodeTypes}
        connectionLineStyle={Arrow.style}
        connectionLineType={Arrow.type}
      >
        <Controls
          className='controls'
          orientation="horizontal"
          position='bottom-right'
          style={{
            boxShadow: "0 0 5px rgba(0, 0, 0, 0.2)",
            position: "fixed",
            bottom: "10px"
          }}
        />
      </ReactFlow>
    </div>
  );
};

export default SimulationArea;