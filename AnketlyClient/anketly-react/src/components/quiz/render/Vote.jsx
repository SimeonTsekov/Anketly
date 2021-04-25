import React, { useContext, useEffect, useState } from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import authService from "../../../services/AuthService";
import { AuthContext, AuthProvider } from "../../../providers/AuthProvider";
import quizService from "../../../services/QuizService";
import {
  FormControl,
  FormLabel,
  List,
  ListItem,
  Radio,
  RadioGroup,
} from "@material-ui/core";
import { useLocation, useParams } from "react-router";

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(15),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function Vote() {
  const classes = useStyles();
  const [quiz, setQuiz] = useState("");
  const [questions, setQuestions] = useState([]);
  const [values, setValues] = useState([]);
  const state = useLocation();
  const uuid = state.pathname.substr(9);

  useEffect(() => {
    quizService
      .getQuizByUuid(uuid)
      .then((quiz) => {
        setQuiz(quiz);
        setQuestions(quiz.questionEntities);
      })
      .catch((error) => console.log("Error: " + error));
  }, []);

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          {quiz.name}
        </Typography>
        <Grid container direction="column" justify="center">
          <List>
            {questions.map((question, index) => (
              <ListItem key={index}>
                <Typography component="h1" variant="h5">
                  {question.required && <>*</>}
                </Typography>
                {question.multipleChoice === false && (
                  <>
                    <FormControl component="fieldset">
                      <FormLabel component="legend">
                        {question.description}
                      </FormLabel>
                      <RadioGroup
                        aria-label={question.description}
                        name={question.description}
                        value={values[index]}
                      >
                        <List>
                          {question.answerEntities.map((answer, index) => (
                            <ListItem key={index}>
                              <FormControlLabel
                                value={answer.description}
                                control={<Radio />}
                                label={answer.description}
                              />
                            </ListItem>
                          ))}
                        </List>
                      </RadioGroup>
                    </FormControl>
                  </>
                )}
              </ListItem>
            ))}
          </List>
        </Grid>
      </div>
    </Container>
  );
}
