import { TILES, antecedantPoint, pointProjection } from "../commons";

function fillDungeon(
  tiles,
  { dungeon, player, objects: dungeonObjects },
  rect
) {
  const { startX, startY, width } = rect;
  const { currentLevel, visibles } = player;
  const objects = dungeonObjects[currentLevel];
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const visibleObjects = objects.reduce(function (a, o) {
    const { position } = o;
    return visibles.indexOf(position) !== -1 ? [...a, o] : a;
  }, []);

  visibleObjects.forEach(function (tile) {
    const { position } = tile;
    const [x, y] = antecedantPoint(position, dungeonWidth);
    const tilePos = pointProjection([x - startX, y - startY], width);
    tiles[tilePos] = { ...TILES.simpleObject };
  });

  return tiles;
}

export default fillDungeon;
