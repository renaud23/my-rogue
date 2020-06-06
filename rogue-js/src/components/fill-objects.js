import { TILES, antecedentPoint, pointProjection } from "../commons";
import { TYPE_OBJECT } from "../game/objects";

function getTile(o) {
  const { type } = o;
  switch (type) {
    case TYPE_OBJECT.chest:
      return { ...TILES.chest };
    case TYPE_OBJECT.key:
      return { ...TILES.key };
    case TYPE_OBJECT.ammo:
      return { ...TILES.ammo };
    default:
      return { ...TILES.simpleObject };
  }
}

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

  visibleObjects.forEach(function (o) {
    const { position } = o;
    const [x, y] = antecedentPoint(position, dungeonWidth);
    const tilePos = pointProjection([x - startX, y - startY], width);

    tiles[tilePos] = getTile(o);
  });

  return tiles;
}

export default fillDungeon;
