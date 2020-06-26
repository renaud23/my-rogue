import { getObjectTex } from "./texture-mapping";
import { isVisibleForPlayer, isInPlayerMemory } from "../../game/player";
import { getObjects } from "../../game/objects/dungeon-objects";

function fill(state, offscreen, texture, rect, size = 8) {
  const { dungeon, player, objects } = state;
  const { currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const { x, y, width, height } = rect;
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

  //   new Array(width * height).fill(0).forEach(function (_, i) {
  //     const xi = i % width;
  //     const yi = Math.trunc(i / width);
  //     const pos = x + xi + (y + yi) * dungeonWidth;

  //     if (isVisibleForPlayer(player, pos) || isInPlayerMemory(player, pos)) {
  //       const groundWall = getWallsText(wall_maps[pos]);

  //       const groundTex = getGroundTex();

  //       offscreen.drawTexture(
  //         texture,
  //         groundTex.x,
  //         groundTex.y,
  //         groundTex.width,
  //         groundTex.height,
  //         xi * size,
  //         yi * size,
  //         size,
  //         size
  //       );
  //       if (groundWall) {
  //         offscreen.drawTexture(
  //           texture,
  //           groundWall.x,
  //           groundWall.y,
  //           groundWall.width,
  //           groundWall.height,
  //           xi * size,
  //           yi * size,
  //           size,
  //           size
  //         );
  //       }
  //     }
  //   });
}

export default fill;
