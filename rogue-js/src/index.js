import React from "react";
import ReactDOM from "react-dom";
import { Console } from "js/components";
import { createWorld } from "js/rogue";
import { createLoop } from "js/rogue";
import registerServiceWorker from "./registerServiceWorker";

const world = createWorld(150, 150);
const renderer = createLoop(world, 40, 30);

ReactDOM.render(<Console renderer={renderer} />, document.getElementById("root"));
registerServiceWorker();
