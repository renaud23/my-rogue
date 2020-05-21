import { getTile } from "../commons";

function fillDungeon(tiles, { dungeon, player }, rect) {
  const { startX, startY, width } = rect;
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const dungeonWidth = dungeon.getWidth(currentLevel);

  return tiles.map(function (_, i) {
    const px = i % width;
    const py = Math.trunc(i / width);
    const tilePos = startX + px + (startY + py) * dungeonWidth;
    return { ...getTile(data[tilePos]), color: "black" };
  });
}

export default fillDungeon;
