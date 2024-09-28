import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import './index.css'
import Layout from './Pages/Layout.jsx'

const router = createBrowserRouter([
  {
  path: "/",
  element: <Layout />,
  /* errorElement: <ErrorPage />, */
  children: [
    {
      path: "/v",
      element: <div>welcome to our page, here stands a lot of extremely valuable information, just for you</div>
    },
  ],
  },
]);


const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
