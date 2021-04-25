import axios from "axios";

class QuizService {
  createQuiz = async function (quiz) {
    console.log(JSON.stringify(quiz));
    const response = await axios.post(
      `http://localhost:8081/createQuiz`,
      quiz,
      {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      }
    );
  };

  getUserQuizzes = function () {
    return new Promise((resolve, reject) => {
      axios
        .get(`http://localhost:8081/getUserQuizzes`, {
          headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
        })
        .then((response) => {
          const quizzes = response.data;
          resolve(quizzes);
        })
        .catch((error) => {
          reject(`Someting went wrong with getting public quizzes: ${error}`);
        });
    });
  };

  getQuizByUuid = function (uuid) {
    return new Promise((resolve, reject) => {
      axios
        .get(`http://localhost:8081/getQuizByUuid/${uuid}`)
        .then((response) => {
          const quiz = response.data;
          resolve(quiz);
        })
        .catch((error) => {
          reject(`Someting went wrong with getting quiz by uuid: ${error}`);
        });
    });
  };
}

export default new QuizService();
