import { peekOne } from "../commons";
import getVisibles from "./get-visibles";

const DEFAULT_FOV = 5;
const DEFAULT_NB_MOVE = 2;

function createPlayer(dungeon, fov = DEFAULT_FOV, maxMove = DEFAULT_NB_MOVE) {
  const currentLevel = 0;
  const position = dungeon.peekEmptyTile(currentLevel);
  const player = {
    position,
    fov,
    action: null,
    currentLevel,
    turn: {
      moveLeft: maxMove,
      turnPlay: 0,
      maxMove: maxMove,
    },
  };
  return { ...player, visibles: getVisibles({ player, dungeon }) };
}

export function nextTurn(player) {
  const { turn } = player;
  const { turnPlay, maxMove } = turn;
  return {
    ...player,
    turn: { ...turn, moveLeft: maxMove, turnPlay: turnPlay + 1 },
  };
}

export function consumeMove(player) {
  const { turn } = player;
  const { moveLeft } = turn;
  return { ...player, turn: { ...turn, moveLeft: moveLeft - 1 } };
}

export function isTurnFinish(player) {
  const { turn } = player;
  const { moveLeft } = turn;
  return moveLeft <= 0;
}

export default createPlayer;
