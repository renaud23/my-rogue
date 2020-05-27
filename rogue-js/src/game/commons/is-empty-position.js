import { TILES } from "../../commons";

function isEnnemy(state, level, position) {
  const { ennemies } = state;
  if (!ennemies || ennemies.length === 0) {
    return false;
  }
  return ennemies[level].reduce(function (a, ennemy) {
    const { position: ennemyPosition } = ennemy;

    return a || position === ennemyPosition;
  }, false);
}

function isEmpty(state, level, position) {
  const { dungeon } = state;
  if (isEnnemy(state, level, position)) {
    return false;
  }

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

export default isEmpty;
