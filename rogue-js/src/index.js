import React from "react";
import ReactDOM from "react-dom";
import { Console, MapJoueur,Controller } from "js/components";
import { createWorld } from "js/rogue";
import { createLoop } from "js/rogue";
import registerServiceWorker from "./registerServiceWorker";

const world = createWorld(60  , 60);
const renderer = createLoop(world, 30, 15);

ReactDOM.render(
  <Controller renderer={renderer} />,
  document.getElementById("root")
);
registerServiceWorker();
