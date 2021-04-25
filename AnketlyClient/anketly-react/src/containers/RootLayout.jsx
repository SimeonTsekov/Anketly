import { Outlet } from "react-router-dom";
import { isPropertySignature } from "typescript";
import NavBar from "../components/nav/NavBar";

export default function RootLayout(props) {
  return (
    <div>
      <NavBar isHome={props.isHome} />
      <Outlet />
    </div>
  );
}
