import { useState } from 'react';

const SunriseSunsetDeleter = () => {
  const [sunriseSunsetId, setSunriseSunsetId] = useState("");

  const handleDeleteSunriseSunset = async () => {
    await fetch(`http://localhost:8080/api/admin/delete/sunrise-sunset-time/${sunriseSunsetId}`, 
    {
       method: "DELETE",
       headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
        'Content-Type': 'application/json'
      }
    });
  };

  return (
    <div>
      <h3>Delete Sunrise/Sunset</h3>
      <input placeholder="ID" onChange={(e) => setSunriseSunsetId(e.target.value)} />
      <button onClick={handleDeleteSunriseSunset}>Delete Sunrise/Sunset</button>
    </div>
  );
};

export default SunriseSunsetDeleter;
