import RootLayout from "./containers/RootLayout";
import RenderQuizzes from "./components/quiz/render/RenderQuizzes";
import SignIn from "./components/SignIn";
import SignUp from "./components/SignUp";
import { Navigate } from "react-router-dom";
import CreateQuiz from "./components/quiz/create/CreateQuiz";
import Vote from "./components/quiz/render/Vote";

const routes = (authenticated) => [
  {
    path: "homepage",
    element: authenticated ? (
      <RootLayout isHome={true} />
    ) : (
      <Navigate to="/login" />
    ),
    children: [
      { path: "quizzes", element: <RenderQuizzes /> },
      { path: "createQuiz", element: <CreateQuiz /> },
    ],
  },
  {
    path: "quizzes",
    element: <RootLayout />,
    children: [{ path: "/:uuid", element: <Vote /> }],
  },
  {
    path: "/",
    element: !authenticated ? (
      <RootLayout />
    ) : (
      <Navigate to="/homepage/quizzes" />
    ),
    children: [
      { path: "login", element: <SignIn /> },
      { path: "register", element: <SignUp /> },
      { path: "/", element: <Navigate to="/login" /> },
    ],
  },
];

export default routes;
