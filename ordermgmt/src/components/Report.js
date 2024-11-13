import React, {useState} from 'react';
import Navbar from './Navbar';

function Report() {
  const [activeTab, setActiveTab] = useState('report');
  return (
    <div>
      <Navbar activeTab={activeTab} setActiveTab={setActiveTab}/>
      Report page
    </div>
  );
}

export default Report;
