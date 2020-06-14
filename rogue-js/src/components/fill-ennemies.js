import { TILES, antecedentPoint, pointProjection } from "../commons";
import { TYPE_ENNEMIES } from "../game/ennemies/commons/type-ennemies";

function getTile(type) {
  switch (type) {
    case TYPE_ENNEMIES.rat:
      return TILES.rat;
    case TYPE_ENNEMIES.wolf:
      return TILES.wolf;
    case TYPE_ENNEMIES.bowman:
      return TILES.bowman;
    default:
      return TILES.enemy;
  }
}

function fillEnnemies(tiles, state, rect) {
  const { ennemies, player, dungeon } = state;

  const { startX, startY, width } = rect;
  const { currentLevel, visibles } = player;
  const ennemiesLevel = ennemies[currentLevel];
  const dungeonWidth = dungeon.getWidth(currentLevel);

  const visibleEnnemies = ennemiesLevel.reduce(function (a, o) {
    const { position } = o;
    return visibles.indexOf(position) !== -1 ? [...a, o] : a;
  }, []);

  visibleEnnemies.forEach(function (o) {
    const { position, type } = o;
    const [x, y] = antecedentPoint(position, dungeonWidth);
    const tilePos = pointProjection([x - startX, y - startY], width);
    tiles[tilePos] = { ...getTile(type), position };
  });

  return tiles;
}

export default fillEnnemies;
