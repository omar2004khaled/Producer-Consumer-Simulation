import '../Style/simulationArea.css';
import React, { useCallback, useState, useEffect } from 'react';
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

  useEffect(() => {
    const socket = new WebSocket('ws://localhost:8080/ws');

    socket.onopen = () => {
      console.log('Connected to the server');
    };

    socket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      const updatedNodes = nodes.map(node => {
        if (node.type === 'Machine') {
          const machine = data.machines.find(m => m.id === parseInt(node.data.id));
          if (machine) {
            return {
              ...node,
              data: { ...node.data, ...machine }
            };
          }
        } else if (node.type === 'Queue') {
          const queue = data.queues.find(q => q.id === parseInt(node.data.id));
          if (queue) {
            return {
              ...node,
              data: { ...node.data, ...queue }
            };
          }
        }
        return node;
      });

      setNodes(updatedNodes);
    };

    socket.onclose = () => {
      console.log('Disconnected from the server');
    };

    return () => {
      socket.close();
    };
  }, [nodes]);

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

      // Update the source and target nodes
      const updatedNodes = nodes.map(node => {
        if (node.id === params.source && node.type === 'Queue') {
          return {
            ...node,
            data: {
              ...node.data,
              inMachines: [...node.data.inMachines, parseInt(params.target)]
            }
          };
        } else if (node.id === params.target && node.type === 'Queue') {
          return {
            ...node,
            data: {
              ...node.data,
              outMachines: [...node.data.outMachines, parseInt(params.source)]
            }
          };
        }
        return node;
      });

      setNodes(updatedNodes);
      setEdges((eds) => addEdge({ ...params, ...Arrow }, eds));
    },
    [setEdges, nodes],
  );

  const addQueue = () => {
    setQueuesNo(queuesNo + 1);
    const newNode = {
      id: `${nodes.length + 1}`,
      type: 'Queue',
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: {
        label: `Queue ${queuesNo + 1}`,
        id: nodes.length + 1,
        type: 'Queue',
        noOfProducts: 0,
        inMachines: [],
        outMachines: []
      },
    };
    setNodes((nds) => [...nds, newNode]);
    console.log(nodes)
  };

  const addMachine = () => {
    setMachinesNo(machinesNo + 1);
    const newNode = {
      id: `${nodes.length + 1}`,
      type: 'Machine',
      position: { x: Math.random() * 400, y: Math.random() * 400 },
      data: {
        label: `Machine ${machinesNo + 1}`,
        id: nodes.length + 1,
        type: 'Machine',
        color: 'none'
      },
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