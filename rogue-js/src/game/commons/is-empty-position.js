import { TILES } from "../../commons";

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

export default function (state, level, position) {
  if (
    isEnemy(state, level, position) ||
    !isEmptyGround(state, level, position)
  ) {
    return false;
  }
  return true;
}
