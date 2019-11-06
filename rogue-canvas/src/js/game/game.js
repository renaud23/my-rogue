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
const playGame = game => game;

/* */
const playPlayer = game => {
  return movePlayer({ ...game, player: reduceFOV(game) });
};

/* */
export const activate = game => {
  return playPlayer(game);
};

/* */
export const consumeStep = game => {
  game.turn = { ...game.turn, stepLeft: Math.max(game.turn.stepLeft - 1, 0) };
  return game;
};

/* */
export const isEndTurn = game => game.turn.stepLeft === 0;

/* */
export const nextTurn = game => {
  const { turn } = game;
  game.turn = { ...turn, stepLeft: turn.step, num: turn.num + 1 };
  return game;
};

/* */
export const createGame = ({ fov = 6 } = { fov: 6 }) => {
  const dungeon = createCave(60, 60);
  const sac = [...dungeon.emptyTiles];
  const position = sac.splice(randomInt(sac.length), 1)[0];
  return {
    turn: {
      step: 0,
      stepLeft: 0,
      num: 0
    },
    action: undefined,
    dungeon,
    player: { ...createPlayer({ fov }), position },
    ennemies: []
  };
};
