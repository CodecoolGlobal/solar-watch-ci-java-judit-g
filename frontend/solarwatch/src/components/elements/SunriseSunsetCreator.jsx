import { useState } from 'react';

const SunriseSunsetCreator = () => {
  const [sunriseSunsetData, setSunriseSunsetData] = useState({
    sunrise: "",
    sunset: "",
    date: "",
    cityName: ""
  });

  const handleCreateSunriseSunset = async () => {
    const data = {
      sunrise: sunriseSunsetData.sunrise,
      sunset: sunriseSunsetData.sunset,
      date: sunriseSunsetData.date,
      newSunriseSunsetInformationCityNameDTO: {
        cityName: sunriseSunsetData.cityName
      }
    };

    try {
      const response = await fetch("http://localhost:8080/api/admin/create/sunrise-sunset-time", {
        method: "POST",
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        alert("Sunrise/Sunset information created successfully!");
      } else {
        alert("Failed to create sunrise/sunset information. Ensure the city exists.");
      }
    } catch (error) {
      console.error("Error creating sunrise/sunset information:", error);
      alert("An error occurred while creating sunrise/sunset information.");
    }
  };

  return (
    <div>
      <h3>Create Sunrise/Sunset</h3>
      <input
        placeholder="Sunrise"
        value={sunriseSunsetData.sunrise}
        onChange={(e) => setSunriseSunsetData({ ...sunriseSunsetData, sunrise: e.target.value })}
      />
      <input
        placeholder="Sunset"
        value={sunriseSunsetData.sunset}
        onChange={(e) => setSunriseSunsetData({ ...sunriseSunsetData, sunset: e.target.value })}
      />
      <input
        placeholder="Date (YYYY-MM-DD)"
        value={sunriseSunsetData.date}
        onChange={(e) => setSunriseSunsetData({ ...sunriseSunsetData, date: e.target.value })}
      />
      <input
        placeholder="City Name"
        value={sunriseSunsetData.cityName}
        onChange={(e) => setSunriseSunsetData({ ...sunriseSunsetData, cityName: e.target.value })}
      />
      <button onClick={handleCreateSunriseSunset}>Create Sunrise/Sunset</button>
    </div>
  );
};

export default SunriseSunsetCreator;
