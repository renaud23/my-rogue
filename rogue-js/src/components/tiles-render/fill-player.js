import { getPlayerTex, getIronSightTex } from "./texture-mapping";
import { isInPlayerMemory } from "../../game/player";

function fill(state, offscreen, texture, rect, size = 8) {
  const { dungeon, player } = state;
  const { currentLevel, position, action = {} } = player;
  const { position: sight } = action;
  const dungeonWidth = dungeon.getWidth(currentLevel);

  const xi = (position % dungeonWidth) - rect.x;
  const yi = Math.trunc(position / dungeonWidth) - rect.y;
  const tex = getPlayerTex();
  // console.log(
  //   "player",
  //   position % dungeonWidth,
  //   Math.trunc(position / dungeonWidth)
  // );
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
