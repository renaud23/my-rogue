import React from "react";
import ReactDOM from "react-dom";
import { Controller } from "js/components";
import { createEventdispatcher, createRenderer, createMonster, createWorld, MONSTER_TYPE } from "js/rogue";
import registerServiceWorker from "./registerServiceWorker";

const world = createWorld(60, 60);
const renderer = createRenderer(world, 40, 30);
const event = createEventdispatcher(world);
for (let i = 0; i < 12; i++) {
  const s = world.peekRandomPlace();
  world.addMonster(createMonster(MONSTER_TYPE.WOLF, s.x, s.y));
}

for (let i = 0; i < 2; i++) {
  const s = world.peekRandomPlace();
  world.addMonster(createMonster(MONSTER_TYPE.GHOUL, s.x, s.y));
}

ReactDOM.render(
  <Controller event={event} renderer={renderer} joueur={world.joueur} world={world} />,
  document.getElementById("root")
);
registerServiceWorker();
