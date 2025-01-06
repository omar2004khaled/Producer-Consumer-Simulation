import '../Style/simulationArea.css';
import React, { useCallback, useState, useEffect, useRef } from 'react';
import '@xyflow/react/dist/style.css';
import { saveAs } from 'file-saver';

import {
  ReactFlow,
  Controls,
  useNodesState,
  useEdgesState,
  addEdge,
} from '@xyflow/react';

import Machine from './Machine';
import Queue from './Queue';
import { Arrow } from './Arrow';
import ControlPanel from './ControlPanel';
import axios from 'axios';

const nodeTypes = {
  Machine: Machine,
  Queue: Queue,
};

const SimulationArea = ({ products }) => {
  const [simulated, setSimulated] = useState(false);
  const [queuesNo, setQueuesNo] = useState(0);
  const [machinesNo, setMachinesNo] = useState(0);
  const [nodes, setNodes, onNodesChange] = useNodesState([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);
  const undoStack = useRef([]);
  const redoStack = useRef([]);

  useEffect(() => {
    const socket = new WebSocket('ws://localhost:8080/ws');

    socket.onopen = () => {
      console.log('Connected to the server');
    };

    socket.onmessage = (event) => {
      console.log('Message from server:', event.data);
      try {
        const data = JSON.parse(event.data);
        setNodes(prevNodes => {
          return prevNodes.map(node => {
            if (node.type === 'Machine') {
              const machine = data.machines.find(m => m.id === node.data.id.toString());
              if (machine) {
                console.log("hello");
                console.log(machine);
                return {
                  ...node,
                  data: { ...node.data, ...machine }
                };
              }
            } else if (node.type === 'Queue') {
              const queue = data.queues.find(q => q.id === node.data.id.toString());
              if (queue) {
                return {
                  ...node,
                  data: { ...node.data, ...queue }
                };
              }
            }
            return node;
          });
        });
      } catch (error) {
        console.error('Error parsing message from server:', error);
      }
    };

    socket.onclose = () => {
      console.log('Disconnected from the server');
    };

    socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    return () => {
      console.log('Closing WebSocket connection');
      socket.close();
    };
  }, []);

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
        } else if (node.id === params.target && node.type === 'Machine') {
          return {
            ...node,
            data: {
              ...node.data,
              nextQueue: parseInt(params.source)
            }
          };
        } else if (node.id === params.source && node.type === 'Machine') {
          return {
            ...node,
            data: {
              ...node.data,
              inQueues: [...node.data.inQueues, parseInt(params.target)]
            }
          };
        }
        return node;
      });

      setNodes(updatedNodes);
      setEdges((eds) => addEdge({ ...params, ...Arrow }, eds));
      saveState();
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
        name: 'Queue',
        noOfProducts: 0,
        inMachines: [],
        outMachines: []
      },
    };
    setNodes((nds) => [...nds, newNode]);
    saveState();
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
        name: 'Machine',
        color: 'none',
        nextQueue: null,
        inQueues: []
      },
    };
    setNodes((nds) => [...nds, newNode]);
    saveState();
  };

  const clearAll = () => {
    setMachinesNo(0);
    setQueuesNo(0);
    setNodes([]);
    setEdges([]);
    saveState();
  };

  const stopSimulation = () => {
    // Reset all nodes by iterating over them and setting data to empty and color to white
    const updatedNodes = nodes.map((node) => {
      if (node.type === 'Machine') {
        return {
          ...node,
          data: {
            ...node.data,
            color: 'white', // Set color to white
            noOfProducts: 0, // Reset product count or any other relevant data
            inQueues: [], // Clear inQueues
            nextQueue: null, // Clear nextQueue
          },
        };
      } else if (node.type === 'Queue') {
        return {
          ...node,
          data: {
            ...node.data,
            color: 'white', // Set color to white
            noOfProducts: 0, // Reset product count
            inMachines: [], // Clear inMachines
            outMachines: [], // Clear outMachines
          },
        };
      }
      return node;
    });
  
    // Update the nodes state with the reset values
    setNodes(updatedNodes);
  
    // Optionally: send a request to stop the simulation on the backend
    axios.post('http://localhost:8080/produce/endSimulation');
  
    // Save the state after stopping the simulation
    saveState();
  };
  
  const simulate = () => {
    console.log(nodes.map(node => node.data));
    axios.post(`http://localhost:8080/produce/simulation/${products}`, nodes.map(node => node.data));
    setSimulated(true);
  };

  const resimulate = () => {
    if (simulated) {
      axios.post(`http://localhost:8080/produce/reSimulation`);
    }
  };


  const continueSimulation = () => {
    if (simulated) {
      axios.post(`http://localhost:8080/produce/continueSimulation`);
    }
  };

  const saveSimulation = async () => {
    const simulationState = {
      nodes,
      edges,
      queuesNo,
      machinesNo
    };
    const options = {
      types: [
        {
          description: 'JSON Files',
          accept: {
            'application/json': ['.json'],
          },
        },
      ],
    };
    try {
      const handle = await window.showSaveFilePicker(options);
      const writable = await handle.createWritable();
      await writable.write(JSON.stringify(simulationState, null, 2));
      await writable.close();
    } catch (error) {
      console.error('Error saving file:', error);
    }
  };

  const loadSimulation = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      const savedState = JSON.parse(e.target.result);
      const { nodes, edges, queuesNo, machinesNo } = savedState;
      setNodes(nodes);
      setEdges(edges);
      setQueuesNo(queuesNo);
      setMachinesNo(machinesNo);
    };
    reader.readAsText(file);
  };

  const saveState = () => {
    undoStack.current.push({
      nodes: [...nodes],
      edges: [...edges],
      queuesNo,
      machinesNo
    });
    redoStack.current = [];
  };

  const undo = () => {
    if (undoStack.current.length > 0) {
      const currentState = {
        nodes: [...nodes],
        edges: [...edges],
        queuesNo,
        machinesNo
      };
      redoStack.current.push(currentState);
      const previousState = undoStack.current.pop();
      setNodes(previousState.nodes);
      setEdges(previousState.edges);
      setQueuesNo(previousState.queuesNo);
      setMachinesNo(previousState.machinesNo);
    }
  };

  const redo = () => {
    if (redoStack.current.length > 0) {
      const currentState = {
        nodes: [...nodes],
        edges: [...edges],
        queuesNo,
        machinesNo
      };
      undoStack.current.push(currentState);
      const nextState = redoStack.current.pop();
      setNodes(nextState.nodes);
      setEdges(nextState.edges);
      setQueuesNo(nextState.queuesNo);
      setMachinesNo(nextState.machinesNo);
    }
  };

  return (
    <div className='react-flow-container'>
      <ControlPanel
        onSimulate={simulate}
        onResimulate={resimulate}
        onClear={clearAll}
        onStop={stopSimulation}
        onContinue={continueSimulation}
        onaddQueue={addQueue}
        onaddMachine={addMachine}
        onSave={saveSimulation}
        onLoad={() => document.getElementById('fileInput').click()}
        onUndo={undo}
        onRedo={redo}
      />
      <input
        id="fileInput"
        type="file"
        accept=".json"
        style={{ display: 'none' }}
        onChange={loadSimulation}
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