import { TILES, antecedentPoint, pointProjection } from "../commons";

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

  visibleEnnemies.forEach(function (tile) {
    const { position } = tile;
    const [x, y] = antecedentPoint(position, dungeonWidth);
    const tilePos = pointProjection([x - startX, y - startY], width);
    tiles[tilePos] = { ...TILES.ennemy, color: "magenta" };
  });

  return tiles;
}

export default fillEnnemies;
