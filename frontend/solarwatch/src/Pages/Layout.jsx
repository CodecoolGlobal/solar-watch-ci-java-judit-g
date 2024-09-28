import { Outlet } from "react-router-dom";

function Layout() {
  return (
  <div>
    <nav>
      <ul>
        <li>
          Test1
        </li>
        <li>
          Test2
        </li>
      </ul>
    </nav>
    <Outlet />
  </div>
  )
}

export default Layout;