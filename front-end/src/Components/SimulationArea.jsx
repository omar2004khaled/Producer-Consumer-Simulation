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
import ControlPanel from './ControlPanel';

const nodeTypes = {
  circle: CircleNode,
  rectangle: RectangleNode,
};

const SimulationArea = () => {
  const [nodes, setNodes, onNodesChange] = useNodesState([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);

  const onConnect = useCallback(
    (params) => setEdges((eds) => addEdge({ ...params, ...Arrow }, eds)),
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