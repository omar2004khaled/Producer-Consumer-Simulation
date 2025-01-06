import React, { useState } from 'react';
import { FaCog, FaPlay, FaRedo, FaTrashAlt, FaLink } from 'react-icons/fa';
import { MdQueue, MdSettingsInputComponent } from 'react-icons/md';
import '../Style/ControlPanel.css';

const ControlPanel = ({ onSimulate, onResimulate, onClear, onaddQueue, onaddMachine }) => {
  const [activeMenu, setActiveMenu] = useState('Simulate');

  const handleMenuClick = (menu) => {
    setActiveMenu(menu);
    if (menu === 'Simulate') onSimulate();
    if (menu === 'Resimulate') onResimulate();
    if (menu === 'Clear') onClear();
    if (menu === 'Queue') onaddQueue();
    if (menu === 'Machine') onaddMachine();
  };

  return (
    <div className="control-panel">
      <div className="control-items">
        <div
          className={`control-item ${activeMenu === 'Queue' ? 'active' : ''}`}
          onClick={() => handleMenuClick('Queue')}
        >
          <MdQueue className="control-icon" />
          <span>Queue</span>
        </div>
        <div
          className={`control-item ${activeMenu === 'Machine' ? 'active' : ''}`}
          onClick={() => handleMenuClick('Machine')}
        >
          <MdSettingsInputComponent className="control-icon" />
          <span>Machine</span>
        </div>
        {/* <div className="control-item">
          <FaLink className="control-icon" />
          <span>Link</span>
        </div> */}
        <div className="separator"></div> {/* Separator */}
        <div
          className={`control-item ${activeMenu === 'Simulate' ? 'active' : ''}`}
          onClick={() => handleMenuClick('Simulate')}
        >
          <FaPlay className="control-icon" />
          <span>Simulate</span>
        </div>
        <div
          className={`control-item ${activeMenu === 'Resimulate' ? 'active' : ''}`}
          onClick={() => handleMenuClick('Resimulate')}
        >
          <FaRedo className="control-icon" />
          <span>Resimulate</span>
        </div>
        <div
          className={`control-item ${activeMenu === 'Clear' ? 'active' : ''}`}
          onClick={() => handleMenuClick('Clear')}
        >
          <FaTrashAlt className="control-icon" />
          <span>Clear</span>
        </div>
      </div>
    </div>
  );
};

export default ControlPanel;