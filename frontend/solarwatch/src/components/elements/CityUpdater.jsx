import { useState } from "react";

const CityUpdater = () => {
  const [cityData, setCityData] = useState({ cityName: "", longitude: "", latitude: "", state: "", country: "" });
  const [cityId, setCityId] = useState("");

  const handleUpdateCity = async () => {
    await fetch(`http://localhost:8080/api/admin/update/city/${cityId}`, {
      method: "PUT",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(cityData),
    });
  };

  return (
    <div>
      <h3>Update City</h3>
      <input placeholder="City Name" onChange={(e) => setCityData({ ...cityData, cityName: e.target.value })} />
      <input placeholder="Longitude" onChange={(e) => setCityData({ ...cityData, longitude: e.target.value })} />
      <input placeholder="Latitude" onChange={(e) => setCityData({ ...cityData, latitude: e.target.value })} />
      <input placeholder="State" onChange={(e) => setCityData({ ...cityData, state: e.target.value })} />
      <input placeholder="Country" onChange={(e) => setCityData({ ...cityData, country: e.target.value })} />
      <input placeholder="ID" onChange={(e) => setCityId(e.target.value)} />
      <button onClick={handleUpdateCity}>Update City</button>
    </div>
  );
};

export default CityUpdater;
