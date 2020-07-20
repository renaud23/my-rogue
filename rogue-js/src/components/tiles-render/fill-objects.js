import { getObjectTex } from "./texture-mapping";
import { isVisibleForPlayer, isInPlayerMemory } from "../../game/player";
import { getObjects } from "../../game/objects/dungeon-objects";

function fill(state, offscreen, texture, rect, size = 8) {
  const { dungeon, player, objects } = state;
  const { currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const { x, y } = rect;
  getObjects(objects, currentLevel).forEach(function (object) {
    const { position } = object;
    if (isVisibleForPlayer(player, position)) {
      const tex = getObjectTex(object);
      const xi = (position % dungeonWidth) - x;
      const yi = Math.trunc(position / dungeonWidth) - y;

      offscreen.drawTexture(
        texture,
        tex.x,
        tex.y,
        tex.width,
        tex.height,
        xi * size,
        yi * size,
        size,
        size
      );
    }
  });
}

export default fill;
