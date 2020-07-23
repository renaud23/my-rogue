import { getWallsText, getGroundTex } from "./texture-mapping";
import { isVisibleForPlayer, isInPlayerMemory } from "../../game/player";

function fill(state, offscreen, texture, rect, size = 8) {
  const { dungeon, player } = state;
  const { currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const tilesInfo = dungeon.getTilesInfo(currentLevel);

  const { x, y, width, height } = rect;

  new Array(width * height).fill(0).forEach(function (_, i) {
    const xi = i % width;
    const yi = Math.trunc(i / width);
    const pos = x + xi + (y + yi) * dungeonWidth;

    if (isVisibleForPlayer(player, pos) || isInPlayerMemory(player, pos)) {
      const { wallCode, biome } = tilesInfo[pos];
      const groundWall = getWallsText(wallCode, biome);
      const groundTex = getGroundTex(biome);
      if (groundWall) {
        offscreen.drawTexture(
          texture,
          groundWall.x,
          groundWall.y,
          groundWall.width,
          groundWall.height,
          xi * size,
          yi * size,
          size,
          size
        );
      } else {
        offscreen.drawTexture(
          texture,
          groundTex.x,
          groundTex.y,
          groundTex.width,
          groundTex.height,
          xi * size,
          yi * size,
          size,
          size
        );
      }
    }
  });
}

export default fill;
