import axios from "axios";

class AuthService {
  register = async function (username, password) {
    const response = await axios.post(`http://localhost:8081/register`, {
      username: username,
      password: password,
    });

    const token = response.data.token;

    if (token !== undefined) {
      localStorage.setItem("token", token);
      return token;
    }
  };

  login = async function (username, password) {
    const response = await axios.post(`http://localhost:8081/login`, {
      username: username,
      password: password,
    });

    const token = response.data.token;

    if (token !== undefined) {
      localStorage.setItem("token", token);
      return token;
    }
  };
}

export default new AuthService();
