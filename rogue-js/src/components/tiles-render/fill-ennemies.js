import { getEnemyTex } from "./texture-mapping";
import { isVisibleForPlayer } from "../../game/player";

function fill(state, offscreen, texture, rect, size = 8) {
  const { dungeon, player, ennemies } = state;
  const { currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const ennemiesLevel = ennemies[currentLevel];

  ennemiesLevel.forEach(function (enemy) {
    const { position, type } = enemy;

    const tex = getEnemyTex(enemy);
    const xi = (position % dungeonWidth) - rect.x;
    const yi = Math.trunc(position / dungeonWidth) - rect.y;
    if (isVisibleForPlayer(player, position)) {
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
