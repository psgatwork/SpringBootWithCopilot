import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AddClinical = () => {
  const [clinicalData, setClinicalData] = useState([]);
  const [formData, setFormData] = useState({
    parameter: '',
    value: ''
  });

  useEffect(() => {
    fetchClinicalData();
  }, []);

  const fetchClinicalData = async () => {
    try {
      const response = await axios.get('/api/clinical-data'); // Replace with your REST endpoint URL
      setClinicalData(response.data);
    } catch (error) {
      console.error('Error fetching clinical data:', error);
    }
  };

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSave = async () => {
    try {
      await axios.post('/api/clinical-data', formData); // Replace with your REST endpoint URL
      fetchClinicalData(); // Update table with new data
      setFormData({ parameter: '', value: '' }); // Clear form inputs
    } catch (error) {
      console.error('Error saving clinical data:', error);
    }
  };

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>Parameter</th>
            <th>Value</th>
          </tr>
        </thead>
        <tbody>
          {clinicalData.map((data) => (
            <tr key={data.id}>
              <td>{data.parameter}</td>
              <td>{data.value}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <form>
        <label>
          Parameter:
          <input type="text" name="parameter" value={formData.parameter} onChange={handleInputChange} />
        </label>
        <label>
          Value:
          <input type="text" name="value" value={formData.value} onChange={handleInputChange} />
        </label>
        <button type="button" onClick={handleSave}>Save</button>
      </form>
    </div>
  );
};

export default AddClinical;


