import { TILES } from "../../commons";
import { TYPE_OBJECT } from "../objects";
import { getObjectsAt } from "./get-at-position";

export function isEnemy(state, level, position) {
  const { ennemies } = state;
  if (!ennemies || ennemies.length === 0) {
    return false;
  }
  return ennemies[level].reduce(function (a, enemy) {
    const { position: enemyPosition } = enemy;

    return a || position === enemyPosition;
  }, false);
}

function isEmptyGround(state, level, position) {
  const { dungeon } = state;

  const data = dungeon.getData(level);
  switch (data[position]) {
    case TILES.ground.code:
    case TILES.stairsDown.code:
    case TILES.stairsUp.code:
    case TILES.stairsUpDown.code:
      return true;

    default:
      return false;
  }
}

function notObstructByObject(state, level, position) {
  return getObjectsAt(state, level, position).reduce(function (a, o) {
    const { type } = o;
    switch (type) {
      case TYPE_OBJECT.door:
        return a && o.opened;
      default:
        return a;
    }
  }, true);
}

export default function (state, level, position) {
  if (
    !notObstructByObject(state, level, position) ||
    isEnemy(state, level, position) ||
    !isEmptyGround(state, level, position)
  ) {
    return false;
  }
  return true;
}
