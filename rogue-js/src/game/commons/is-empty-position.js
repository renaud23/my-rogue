import { TILES } from "../../commons";

function isEmpty(state, position) {
  const { dungeon, player } = state;
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
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
