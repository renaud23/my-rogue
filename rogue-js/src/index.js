import React from "react";
import ReactDOM from "react-dom";
import { Console, MapJoueur } from "js/components";
import { createWorld } from "js/rogue";
import { createLoop } from "js/rogue";
import registerServiceWorker from "./registerServiceWorker";

const world = createWorld(150, 150);
const renderer = createLoop(world, 30, 30);

ReactDOM.render(
  <div renderer={renderer}>
    <Console renderer={renderer} />
    <MapJoueur joueur={world.joueur} />
  </div>,
  document.getElementById("root")
);
registerServiceWorker();
