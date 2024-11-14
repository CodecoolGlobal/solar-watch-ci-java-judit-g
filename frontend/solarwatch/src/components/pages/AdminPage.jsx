import SunriseSunsetUpdater from '../elements/SunriseSunsetUpdater';
import CityUpdater from './../elements/CityUpdater';
import SunriseSunsetDeleter from './../elements/SunriseSunsetDeleter';
import CityDeleter from './../elements/CityDeleter';
import CityCreator from '../elements/CityCreator';
import SunriseSunsetCreator from './../elements/SunriseSunsetCreator';

const AdminPage = () => {
  return (
    <div>
      <h2>Admin Page</h2>
      <SunriseSunsetUpdater />
      <CityUpdater />
      <SunriseSunsetDeleter />
      <CityDeleter />
      <CityCreator />
      <SunriseSunsetCreator />
    </div>
  );
};

export default AdminPage;
