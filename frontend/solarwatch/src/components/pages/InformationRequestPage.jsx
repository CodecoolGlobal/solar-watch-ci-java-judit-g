import { useState } from 'react';
import RequestInformation from '../elements/RequestInformation';
import { TailSpin } from 'react-loader-spinner';
import NavBar from './../elements/Navbar';

function InformationRequestPage() {
  const [city, setCity] = useState('');
  const [date, setDate] = useState('');
  const [information, setInformation] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    const token = localStorage.getItem('jwt');
    setError('');
    setInformation(null);

    try {
      setLoading(true);
      const response = await fetch(`/api/information?city=${city}&date=${date}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (response.status === 404) {
        setError(`No information found for city: ${city}`);
        return;
      }

      const data = await response.json();
      setInformation(data);
    } catch (error) {
      console.error("Error fetching information:", error);
      setError("An error occurred while fetching information.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="information-page">
      <NavBar />
      <RequestInformation
        city={city}
        setCity={setCity}
        date={date}
        setDate={setDate}
        onSubmit={handleSubmit}
      />

      {loading ? (
        <TailSpin color='purple'/>
      ) : (
        <>
          {error && <div className="error-message">{error}</div>}
          {information && (
            <div className="information-result">
              <h3>Information for {information.cityName} on {information.date}</h3>
              <h4>Sunset: {information.sunset}</h4>
              <h4>Sunrise: {information.sunrise}</h4>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default InformationRequestPage;
