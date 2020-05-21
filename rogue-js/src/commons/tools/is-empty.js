import { TILES } from "../constantes";

function isEmpty({ dungeon, player }, pos) {
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  switch (data[pos]) {
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
