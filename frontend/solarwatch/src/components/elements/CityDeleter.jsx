import { useState } from 'react';

const CityDeleter = () => {
  const [cityId, setCityId] = useState("");

  const handleDeleteCity = async () => {
    await fetch(`http://localhost:8080/api/admin/delete/city/${cityId}`, { 
      method: "DELETE",
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
          'Content-Type': 'application/json'
        }
      });
  };

  return (
    <div>
      <h3>Delete City</h3>
      <input placeholder="ID" onChange={(e) => setCityId(e.target.value)} />
      <button onClick={handleDeleteCity}>Delete City</button>
    </div>
  );
};

export default CityDeleter;
