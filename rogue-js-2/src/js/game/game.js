import { createCave } from "./dungeon";
import createPlayer, { reduceFOV } from "./player";
import {
  createBerserk,
  createWorm,
  activate as activateEnnemy
} from "./ennemy";
import { randomInt, isEmptyPosition } from "./commons";

const ARROW = {
  up: "ArrowUp",
  down: "ArrowDown",
  left: "ArrowLeft",
  right: "ArrowRight"
};

const initialiseGame = (ww, wh) => {
  const dungeon = createCave(ww, wh);
  const sac = [...dungeon.emptyTiles];
  const position = sac.splice(randomInt(sac.length), 1)[0];
  const besreserks = new Array(5)
    .fill(0)
    .map(() => createBerserk(sac.splice(randomInt(sac.length), 1)[0]));

  const worms = new Array(2)
    .fill(0)
    .map(() => createWorm(sac.splice(randomInt(sac.length), 1)[0]));
  return {
    dungeon,
    player: { ...createPlayer(), position },
    ennemies: [...besreserks, ...worms]
  };
};

const movePlayerTo = position => game => {
  const { player } = game;
  return { ...game, player: { ...player, position } };
};

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

const reduceView = game => {
  return { ...game, player: reduceFOV(game) };
};

const activateEnnemies = (game = {}) => {
  const { gn, ennemies } = game.ennemies.reduce(
    (a, e) => {
      const { game: gNext, e: eNext } = activateEnnemy(e)(game);
      return { gn: gNext, ennemies: [...a.ennemies, eNext] };
    },
    { gn: game, ennemies: [] }
  );
  return { ...gn, ennemies };
};

const activate = game => {
  return reduceView(movePlayer(activateEnnemies(game)));
};

const initialGame = {
  dungeon: undefined,
  player: undefined,
  action: undefined
};

const createGame = (createRenderer, width = 80, height = 60) => {
  let GAME = initialGame;
  let renderer;
  return {
    start: () => {
      GAME = initialiseGame(width, height);
    },
    activate: () => {
      GAME = activate(GAME);
    },
    render: () => {
      if (renderer) {
        renderer(GAME);
      }
    },
    initialiseRenderer: (dungeonEl, mapEl) => {
      renderer = createRenderer({ dungeonEl, mapEl });
    },

    startAction: action => {
      GAME = { ...GAME, action };
    },
    stopAction: () => {
      GAME = { ...GAME, action: undefined };
    }
  };
};

export default createGame;
