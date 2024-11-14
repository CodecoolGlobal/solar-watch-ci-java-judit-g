function RequestInformation({ city, setCity, date, setDate, onSubmit }) {
  return (
    <div>
      <h2>Request Information</h2>
      <form onSubmit={onSubmit}>
        <div>
          <label>Location:</label>
          <input
            type="text"
            value={city}
            onChange={(e) => setCity(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Date:</label>
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
          />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default RequestInformation;