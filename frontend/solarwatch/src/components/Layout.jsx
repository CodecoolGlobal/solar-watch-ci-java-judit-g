import { Outlet } from "react-router-dom";

const Layout = () => (
  <div className="layout">
    <div className="header">
      <h1>Solarwatch</h1>
      <h2>search for sunrise and sunset information all over the world!</h2>
    </div>
    <Outlet />
  </div>
);

export default Layout;
