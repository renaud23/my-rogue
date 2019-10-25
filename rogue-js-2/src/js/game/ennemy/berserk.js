import { canSee, isEmptyPosition, createChrono } from "../commons";
import { astarPath } from "./path-finding";
export const TYPE_BERSERK = "ennemy/berserk";
// https://www.redblobgames.com/pathfinding/a-star/introduction.html

const BERSERK_SPEED = 250;

const STATE = {
  wait: "berserck/waiting",
  move: "berserk/move"
};

const wait = e => game => {
  const { position } = e;
  return canSee(game)(position, game.player.position)
    ? { game, e: { ...e, state: STATE.move } }
    : { game, e };
};

const checkTimer = e => ({
  ...e,
  timer: e.timer || createChrono(BERSERK_SPEED)
});

const findPlayer = game => {
  return false;
};

const followPath = game => e => {
  const { player } = game;
  const { path } = e;
  if (!path || path.length === 0) {
    return {
      ...e,
      state: STATE.wait,
      path: undefined,
      lastSee: undefined,
      timer: undefined
    };
  }
  const [next, ...rest] = path;
  return isEmptyPosition(game)(next) && next !== player.position
    ? { ...e, position: next, path: rest }
    : { ...e, path: undefined };
};

const move = e => game => {
  const { player } = game;
  const { position, timer, lastSee } = e;
  if (timer()) {
    if (canSee(game)(position, game.player.position)) {
      return {
        game,
        e:
          lastSee === player.position
            ? followPath(game)(e)
            : followPath(game)({
                ...e,
                path: astarPath(game)(position, player.position)
              })
      };
    }
    return { game, e: followPath(game)(e) };
  }

  return { game, e };
};

export const activate = e => game => {
  if (findPlayer(game)) return { game, e };
  const { state } = e;
  switch (state) {
    case STATE.wait:
      return wait(e)(game);
    case STATE.move:
      return move(checkTimer(e))(game);
    default:
      return { game, e };
  }
};

export const getTileAt = ({ position }) => pos =>
  position === pos ? "B" : undefined;

export const createBerserk = position => ({
  state: STATE.wait,
  type: TYPE_BERSERK,
  timer: undefined,
  lastSee: undefined,
  path: undefined,
  position
});

export const isAtPos = ({ position }) => pos => position === pos;
