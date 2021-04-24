import RootLayout from "./containers/RootLayout"
import RenderQuizzes from "./components/RenderQuizzes"
import SignIn from "./components/SignIn";
import SignUp from "./components/SignUp";
import { Navigate } from "react-router-dom";

const routes = (authenticated) => [
    {
      path: "homepage",
      element: authenticated ? <RootLayout /> : <Navigate to="/login" />,
      children: [
        { path: "forms", element: <RenderQuizzes /> }
      ],
    },
    {
      path: "/",
      element: !authenticated ? <RootLayout /> : <Navigate to="/homepage/forms" />,
      children: [
        { path: "login", element: <SignIn /> },
        { path: "register", element: <SignUp /> },
        { path: "/", element: <Navigate to="/login" /> }
      ],
    },
  ];

  export default routes;