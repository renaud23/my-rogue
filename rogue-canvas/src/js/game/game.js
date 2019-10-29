import { createCave } from "./dungeon";
import createPlayer from "./player";
import { randomInt } from "./common-tools";

export const activate = game => {
  return { ...game };
};

export const createGame = () => {
  const dungeon = createCave(60, 60);
  const sac = [...dungeon.emptyTiles];
  const position = sac.splice(randomInt(sac.length), 1)[0];
  return {
    dungeon,
    player: { ...createPlayer(), position }
  };
};
