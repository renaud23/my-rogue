import { peekOne } from "../commons";
import getVisibles from "./get-visibles";

const DEFAULT_FOV = 5;
const NB_MOVE = 2;

function createPlayer(dungeon, fov = DEFAULT_FOV) {
  const currentLevel = 0;
  const position = dungeon.peekEmptyTile(currentLevel);
  const player = {
    position,
    fov,
    action: null,
    currentLevel,
    moveLeft: NB_MOVE,
  };
  return { ...player, visibles: getVisibles({ player, dungeon }) };
}

export function nextTurn(player) {
  return { ...player, moveLeft: NB_MOVE };
}

export function consumeMove(player) {
  return { ...player, moveLeft: Math.max(player.moveLeft - 1, 0) };
}

export function isTurnFinish(player) {
  return player.moveLeft === 0;
}

export default createPlayer;
