import React from "react";
import { render } from "react-dom";
import { Main } from "./js/components";
import startGame from "./js/game";

render(
  <Main launch={startGame} width={208} height={208} />,
  document.getElementById("root")
);
