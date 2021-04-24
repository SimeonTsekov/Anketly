import logo from './logo.svg';
import './App.css';
import { useRoutes } from "react-router-dom";
import routes from './routes';
import { BrowserRouter } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from './providers/AuthProvider';

function App() {
  const {authenticated} = useContext(AuthContext);
  const routing = useRoutes(routes(authenticated));

  return routing;
}

export default App;
