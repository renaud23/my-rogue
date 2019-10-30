import { createCave } from "./dungeon";
import createPlayer, { reduceFOV } from "./player";
import { randomInt, isEmptyPosition } from "./common-tools";

const ARROW = {
  up: "ArrowUp",
  down: "ArrowDown",
  left: "ArrowLeft",
  right: "ArrowRight"
};

/* */
const movePlayerTo = position => game => {
  const { player } = game;
  return { ...game, player: { ...player, position } };
};

/* */
const movePlayer = game => {
  if (game.action) {
    const { player, dungeon } = game;
    switch (game.action) {
      case ARROW.up: {
        const next = player.position - dungeon.width;
        return isEmptyPosition(game)(next) ? movePlayerTo(next)(game) : game;
      }
      case ARROW.down: {
        const next = player.position + dungeon.width;
        return isEmptyPosition(game)(next) ? movePlayerTo(next)(game) : game;
      }
      case ARROW.left: {
        const next = player.position - 1;
        return isEmptyPosition(game)(next) ? movePlayerTo(next)(game) : game;
      }
      case ARROW.right: {
        const next = player.position + 1;
        return isEmptyPosition(game)(next) ? movePlayerTo(next)(game) : game;
      }
      default:
        return game;
    }
  }
  return game;
};

/* */
const playPlayer = game => {
  return movePlayer({ ...game, player: reduceFOV(game) });
};

/* */
export const activate = game => {
  return playPlayer(game);
};

/* */
export const createGame = ({ fov = 6 } = { fov: 6 }) => {
  const dungeon = createCave(60, 60);
  const sac = [...dungeon.emptyTiles];
  const position = sac.splice(randomInt(sac.length), 1)[0];
  return {
    action: undefined,
    dungeon,
    player: { ...createPlayer({ fov }), position },
    ennemies: []
  };
};
