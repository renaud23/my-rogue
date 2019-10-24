import React from "react";
import ReactDOM from "react-dom";
import Game from "./js/component";
import { createGame } from "./js/game";
import {
  createMapRenderer,
  compseRenderer,
  createRenderer
} from "./js/game/renderer";
/* */
const worldWidth = 80;
const worldHeight = 60;
const viewSize = 12;

const viewPortRenderer = createRenderer(viewSize, viewSize);
const mapRenderer = createMapRenderer();
const renderer = compseRenderer(viewPortRenderer, mapRenderer);

const game = createGame(renderer, worldWidth, worldHeight);

ReactDOM.render(<Game game={game} />, document.getElementById("root"));
