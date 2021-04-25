import { Link as RouterLink } from "react-router-dom";
import { AppBar, Toolbar, makeStyles, Button } from "@material-ui/core";

const useStyles = makeStyles({
  toolbar: {
    height: 64,
  },
  nav: {
    color: "white",
  },
});

const NavBar = (props) => {
  const classes = useStyles();

  return (
    <AppBar elevation={0}>
      <Toolbar className={classes.toolbar}>
        {props.isHome && (
          <>
            <RouterLink to="/homepage/createQuiz">
              <Button className={classes.nav}>Create a quiz</Button>
            </RouterLink>
            <RouterLink to="/homepage/quizzes">
              <Button id="quizzes" className={classes.nav}>
                Browse Quizzes
              </Button>
            </RouterLink>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
