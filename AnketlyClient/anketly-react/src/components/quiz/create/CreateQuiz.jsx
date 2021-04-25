import React, { useContext, useState } from "react";
import {
  AppBar,
  Toolbar,
  makeStyles,
  Button,
  Grid,
  Typography,
  CssBaseline,
  Container,
  TextField,
  Link,
  Checkbox,
  FormControlLabel,
  List,
  ListItem,
} from "@material-ui/core";
import { isClassExpression } from "typescript";
import RenderQuizzes from "../render/RenderQuizzes";
import quizService from "../../../services/QuizService";
import ReactImageUploadComponent from "react-images-upload";

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

const CreateQuiz = (props) => {
  const classes = useStyles();

  const [name, setName] = useState("");
  const [isPublic, setIsPublic] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [questionDatas, setQuestionDatas] = useState([]);

  function onQuestionCreated() {
    setQuestions([
      ...questions,
      {
        description: "",
        required: false,
        multipleChoice: false,
        answerModels: [],
      },
    ]);
  }

  function onQuestionData(question) {
    setQuestionDatas([
      ...questionDatas,
      {
        description: question.description,
        required: question.required,
        multipleChoice: question.multipleChoice,
        answerModels: question.answerModels,
      },
    ]);
  }

  function create(event) {
    event.preventDefault();
    quizService.createQuiz({
      name: name,
      isPublic: isPublic,
      questionModels: questionDatas,
    });
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Create Quiz
        </Typography>
        <form className={classes.form} noValidate onSubmit={create}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="name"
            label="Quiz Name"
            name="name"
            autoFocus
            onChange={(event) => setName(event.target.value)}
          />
          <FormControlLabel
            value="isPublic"
            control={
              <Checkbox
                color="primary"
                onChange={(event) => setIsPublic(event.target.checked)}
              />
            }
            label="Public"
            labelPlacement="start"
          />
          <Grid container direction="column" justify="center">
            <List>
              {questions.map((question, index) => (
                <ListItem key={index}>
                  <InputQuestion
                    question={question}
                    index={index + 1}
                    onData={onQuestionData}
                  />
                </ListItem>
              ))}
            </List>
            <Button
              variant="contained"
              color="primary"
              onClick={onQuestionCreated}
            >
              Add Question
            </Button>
          </Grid>

          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Create Quiz
          </Button>
        </form>
      </div>
    </Container>
  );
};

const InputQuestion = (props) => {
  const [description, setDescription] = useState(props.question.description);
  const [required, setRequired] = useState(props.question.required);
  const [multipleChoice, setMultipleChoice] = useState(
    props.question.multipleChoice
  );
  const [imageData, setImageData] = useState("");
  const [answers, setAnswers] = useState(props.question.answerModels);
  const [answerDatas, setAnswerDatas] = useState([]);

  function onAnswerCreated() {
    setAnswers([...answers, { description: "" }]);
  }

  function onAnswerData(answer) {
    setAnswerDatas([...answerDatas, answer]);
  }

  function toBase64(image) {
    new Promise((resolve, _) => {
      const reader = new FileReader();
      reader.onload = () => {
        resolve(reader.result);
      };
      reader.readAsDataURL(image);
    });
  }

  function pickImage(event) {
    const image = event.target.files[0] || null;
    if (!image) {
      setImageData("");
      return;
    }
    toBase64(image).then((base64) => {
      setImageData(base64);
    });
  }

  return (
    <Grid container direction="column" justify="center">
      <Grid item justify="center">
        <Typography component="h2" variant="h5">
          Question {props.index}
        </Typography>
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          id="description"
          label="Question Description"
          name="description"
          autoFocus
          onChange={(event) => setDescription(event.target.value)}
        />
        <div>
          {imageData !== "" && (
            <img width="300" height="300" src={imageData} alt="" />
          )}
          <input type="file" onChange={pickImage} />
        </div>
        <FormControlLabel
          value="isMultipleChoice"
          control={
            <Checkbox
              color="primary"
              onChange={(event) => setMultipleChoice(event.target.checked)}
            />
          }
          label="Multiple Choice"
          labelPlacement="start"
        />
        <FormControlLabel
          value="isRequired"
          control={
            <Checkbox
              color="primary"
              onChange={(event) => setRequired(event.target.checked)}
            />
          }
          label="Required"
          labelPlacement="start"
        />
        <List>
          {answers.map((answer, index) => (
            <ListItem key={index}>
              <InputAnswer
                answer={answer}
                index={index + 1}
                onData={onAnswerData}
              />
            </ListItem>
          ))}
        </List>
        <Button variant="contained" color="primary" onClick={onAnswerCreated}>
          Add Answer
        </Button>
        <Button
          variant="contained"
          color="primary"
          onClick={() => {
            props.onData({
              description: description,
              base64image: imageData,
              required: required,
              multipleChoice: multipleChoice,
              answerModels: answerDatas,
            });
            console.log(answerDatas);
          }}
        >
          Save Question
        </Button>
      </Grid>
    </Grid>
  );
};

const InputAnswer = (props) => {
  const [description, setDescription] = useState(props.description);
  const [disabled, setDisabled] = useState(false);

  function onSave() {
    setDisabled(!disabled);
  }

  return (
    <Grid container direction="column" justify="center">
      <Typography component="h3" variant="h5">
        Answer {props.index}
      </Typography>
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        id="description"
        label="Answer"
        name="description"
        autoFocus
        disabled={disabled}
        onChange={(event) => setDescription(event.target.value)}
      />
      <Button
        variant="contained"
        color="primary"
        disabled={disabled}
        onClick={() => {
          props.onData(description);
          onSave();
        }}
      >
        Save Answer
      </Button>
    </Grid>
  );
};

export default CreateQuiz;
