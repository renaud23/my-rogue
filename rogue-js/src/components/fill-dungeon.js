import { getVisibles } from "../player";
import { getTile, TILES } from "../commons";

function fillDungeon(tiles, { dungeon, player }, rect) {
  const { startX, startY, width, height } = rect;
  const { data } = dungeon;

  return tiles.map(function (_, i) {
    const px = i % width;
    const py = Math.trunc(i / width);
    const tilePos = startX + px + (startY + py) * dungeon.width;

    return { ...getTile(data[tilePos]), color: "black" };
  });

  return tiles;
}

export default fillDungeon;
