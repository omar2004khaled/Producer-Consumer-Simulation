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
  ReactFlowProvider
} from '@xyflow/react';

import CircleNode from './CircleNode';
import RectangleNode from './RectangleNode';
import ControlPanel from './ControlPanel';

const nodeTypes = {
  circle: CircleNode,
  rectangle: RectangleNode
};

const SimulationArea = () => {
  const initialNodes = [
    { id: '1', type: 'circle', position: { x: 0, y: 0 }, data: { label: 'Circle Node' } },
    { id: '2', type: 'rectangle', position: { x: 0, y: 100 }, data: { label: 'Rectangle Node' } },
  ];
  const initialEdges = [{ id: 'e1-2', source: '1', target: '2' }];
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

  const onConnect = useCallback(
    (params) => setEdges((eds) => addEdge(params, eds)),
    [setEdges],
  );

  const addRectangleNode = () => {
    const newNode = {
      id: (nodes.length + 1).toString(),
      type: 'rectangle',
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: { label: `Rectangle Node ${nodes.length + 1}` },
    };
    setNodes((nds) => [...nds, newNode]);
  };

  const addCircleNode = () => {
    const newNode = {
      id: (nodes.length + 1).toString(),
      type: 'circle',
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: { label: `Circle Node ${nodes.length + 1}` },
    };
    setNodes((nds) => [...nds, newNode]);
  };

  return (
    <div className='react-flow-container'>
      <ControlPanel
        onSimulate={() => console.log('Simulate')}
        onResimulate={() => console.log('Resimulate')}
        onClear={() => console.log('Clear')}
        onAddRectangleNode={addRectangleNode}
        onAddCircleNode={addCircleNode}
      />
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        onConnect={onConnect}
        nodeTypes={nodeTypes}
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