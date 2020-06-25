import { getIronSightTex } from "./texture-mapping";
import { isInPlayerMemory, isVisibleForPlayer } from "../../game/player";

function fill(state, offscreen, texture, rect, size = 8) {
  const { dungeon, player } = state;
  const { currentLevel, position, action = {} } = player;
  const { position: sight, rangePositions = [] } = action;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const { x, y, width, height } = rect;

  new Array(width * height).fill(0).forEach(function (_, i) {
    const xi = i % width;
    const yi = Math.trunc(i / width);
    const pos = x + xi + (y + yi) * dungeonWidth;

    if (!isVisibleForPlayer(player, pos) && isInPlayerMemory(player, pos)) {
      offscreen.fillRect(
        "rgba(50,50,50,0.6)",
        xi * size,
        yi * size,
        size,
        size
      );
    }

    if (rangePositions.indexOf(pos) !== -1) {
      offscreen.fillRect(
        "rgba(150,150,50,0.6)",
        xi * size,
        yi * size,
        size,
        size
      );
    }
  });

  if (sight) {
    const sightTex = getIronSightTex();
    const xs = (sight % dungeonWidth) - rect.x;
    const ys = Math.trunc(sight / dungeonWidth) - rect.y;
    offscreen.drawTexture(
      texture,
      sightTex.x,
      sightTex.y,
      sightTex.width,
      sightTex.height,
      xs * size,
      ys * size,
      size,
      size
    );
  }
}

export default fill;
