import React from "react";
import { render } from "react-dom";
import { Main } from "./js/components";
import startGame from "./js/game";

render(<Main launch={startGame} />, document.getElementById("root"));
