import { useState } from 'react';

const SunriseSunsetUpdater = () => {
  const [sunriseSunsetData, setSunriseSunsetData] = useState({ sunrise: "", sunset: "" });
  const [sunriseSunsetId, setSunriseSunsetId] = useState("");

  const handleUpdateSunriseSunset = async () => {
    await fetch(`http://localhost:8080/api/admin/update/sunrise-sunset-time/${sunriseSunsetId}`, {
      method: "PUT",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(sunriseSunsetData),
    });
  };

  return (
    <div>
      <h3>Update Sunrise/Sunset</h3>
      <input placeholder="Sunrise" onChange={(e) => setSunriseSunsetData({ ...sunriseSunsetData, sunrise: e.target.value })} />
      <input placeholder="Sunset" onChange={(e) => setSunriseSunsetData({ ...sunriseSunsetData, sunset: e.target.value })} />
      <input placeholder="ID" onChange={(e) => setSunriseSunsetId(e.target.value)} />
      <button onClick={handleUpdateSunriseSunset}>Update Sunrise/Sunset</button>
    </div>
  );
};

export default SunriseSunsetUpdater;
