import React, { useState } from 'react';
import axios from 'axios';

const DeletePatient = () => {
    const [patientId, setPatientId] = useState('');

    const handleDelete = async () => {
        try {
            await axios.delete(`https://api.example.com/patients/${patientId}`);
            console.log('Patient deleted successfully');
        } catch (error) {
            console.error('Error deleting patient:', error);
        }
    };

    return (
        <div>
            <h2>Delete Patient</h2>
            <input
                type="text"
                value={patientId}
                onChange={(e) => setPatientId(e.target.value)}
                placeholder="Enter patient ID"
            />
            <button onClick={handleDelete}>Delete</button>
        </div>
    );
};

export default DeletePatient;