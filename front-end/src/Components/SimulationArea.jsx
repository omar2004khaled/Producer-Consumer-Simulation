import '../Style/simulationArea.css';
import React, { useCallback, useState } from 'react';
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

import Machine from './Machine';
import Queue from './Queue';
import { Arrow } from './Arrow';
import ControlPanel from './ControlPanel';

const nodeTypes = {
  Machine: Machine,
  Queue: Queue,
};

const SimulationArea = () => {
  const [queuesNo, setQueuesNo] = useState(0);
  const [machinesNo, setMachinesNo] = useState(0);
  const [nodes, setNodes, onNodesChange] = useNodesState([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);

  const onConnect = useCallback(
    (params) => {
      const sourceNode = nodes.find((node) => node.id === params.source);
      const targetNode = nodes.find((node) => node.id === params.target);
  
      if (params.source === params.target) {
        alert("A node cannot connect to itself!");
        return;
      }
  
      if (sourceNode.type === targetNode.type) {
        alert("Source and target nodes must be of different types!");
        return;
      }
  
      setEdges((eds) => addEdge({ ...params, ...Arrow }, eds));
    },
    [setEdges, nodes], // Ensure 'nodes' is included in dependencies
  );
  
  

  const addQueue = () => {
    setQueuesNo(queuesNo + 1)
    const newNode = {
      id: (nodes.length + 1).toString(),
      type: 'Queue',
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: { label: `Queue ${queuesNo + 1}` },
    };
    setNodes((nds) => [...nds, newNode]);
  };

  const addMachine = () => {
    setMachinesNo(machinesNo + 1)
    const newNode = {
      id: (nodes.length + 1).toString(),
      type: 'Machine',
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: { label: `Machine ${machinesNo + 1}` },
    };
    setNodes((nds) => [...nds, newNode]);
  };

  return (
    <div className='react-flow-container'>
      <ControlPanel
        onSimulate={() => console.log('Simulate')}
        onResimulate={() => console.log('Resimulate')}
        onClear={() => console.log('Clear')}
        onaddQueue={addQueue}
        onaddMachine={addMachine}
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