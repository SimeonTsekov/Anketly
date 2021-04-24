
import { useState, createContext } from "react";

export const AuthContext = createContext({});
  
export const AuthProvider = ({ children }) => {
const [token, setToken] = useState(null);
  
return (
    <AuthContext.Provider
    value={{
        token: token,
          authenticated: token !== null,
          setToken: setToken,
        }}
      >
    {children}
    </AuthContext.Provider>
);
};