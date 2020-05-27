import { TILES } from "../../commons";

function isVisible(state, level, position) {
  const { dungeon } = state;

  const data = dungeon.getData(level);
  switch (data[position]) {
    case TILES.ground.code:
    case TILES.stairsDown.code:
    case TILES.stairsUpDown.code:
      return true;

    default:
      return false;
  }
}

export default isVisible;
