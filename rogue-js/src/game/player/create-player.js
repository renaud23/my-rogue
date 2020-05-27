import getVisibles from "./get-visibles";
import createInventory, { putObject } from "./inventory";
import { createKnife } from "../objects";

const DEFAULT_FOV = 10;
const DEFAULT_NB_MOVE = 2;

function createPlayer(dungeon, fov = DEFAULT_FOV, maxMove = DEFAULT_NB_MOVE) {
  const currentLevel = 0;
  const position = dungeon.peekEmptyTile(currentLevel);
  const player = {
    position,
    fov,
    action: null,
    inventory: null,
    currentLevel,
    turn: {
      moveLeft: maxMove,
      turnPlay: 0,
      maxMove: maxMove,
    },
  };

  const inventory = createInventory(10);
  const knife = createKnife();

  return {
    ...player,
    visibles: getVisibles({ player, dungeon, ennemies: [], objects: [] }),
    inventory: putObject(inventory, knife),
  };
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
