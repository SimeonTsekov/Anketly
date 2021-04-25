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
import { List, ListItem } from "@material-ui/core";
import { useNavigate } from "react-router";

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

export default function RenderQuizzes() {
  const classes = useStyles();
  const [userQuizzes, setUserQuizzes] = useState([]);

  useEffect(() => {
    quizService
      .getUserQuizzes()
      .then((quizzes) => {
        setUserQuizzes(quizzes);
      })
      .catch((error) => console.log("Error: " + error));
  }, []);

  const navigate = useNavigate();

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Quizzes
        </Typography>
        <Grid container direction="column" justify="center">
          <List>
            {userQuizzes.map((userQuiz, index) => (
              <ListItem key={index}>
                <Grid item xs={12}>
                  <Typography component="h1" variant="h5">
                    {userQuizzes[index].name}
                  </Typography>
                  <Button
                    onClick={() => {
                      navigate(`/quizzes/${userQuizzes[index].uuid}`, {
                        state: userQuizzes[index].uuid,
                      });
                    }}
                    variant="contained"
                    color="primary"
                  >
                    Vote
                  </Button>
                  <Button variant="contained" color="primary">
                    Browse
                  </Button>
                </Grid>
              </ListItem>
            ))}
          </List>
        </Grid>
      </div>
    </Container>
  );
}
