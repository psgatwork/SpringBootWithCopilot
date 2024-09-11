import './App.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Home from './components/Home';
import AddPatient from './components/AddPatient';
import AddClinicalData from './components/AddClinicalData';
import DeletePatient from './components/DeletePatient';


function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/addPatient" element={<AddPatient />} />
        <Route path="/addClinicals/:patientId" element={<AddClinicalData />} />
        <Route path="/deletePatinet/:patientId" element={<DeletePatient />} />
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
