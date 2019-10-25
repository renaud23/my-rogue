import React from "react";
import { render } from "react-dom";
import { Main } from "./js/components";
import { createGame } from "./js/game";

render(<Main launch={createGame} />, document.getElementById("root"));
