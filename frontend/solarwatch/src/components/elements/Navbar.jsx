import { Link, useNavigate } from "react-router-dom";

function NavBar() {

  const navigate = useNavigate();

  function handleLogout() {
    localStorage.clear();
    navigate("/")
  }

  return (
    <div>
      {
        JSON.parse(localStorage.getItem("roles")).includes("ROLE_ADMIN") && 
        <Link to={"/admin"}>
          <button>Admin</button>
        </Link>
      }
      <button onClick={handleLogout}>Logout</button>
    </div>
  )
}

export default NavBar;