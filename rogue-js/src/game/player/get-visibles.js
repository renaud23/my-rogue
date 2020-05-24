import { distanceEucl, getSegment } from "../../commons";
import { isEmptyPosition } from "../commons";

function getVisibles({ player, dungeon }) {
  const { fov, position, currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const height = dungeon.getHeight(currentLevel);
  const px = position % width;
  const py = Math.trunc(position / width);
  const startX = Math.max(px - fov, 0);
  const startY = Math.max(py - fov, 0);
  const stopX = Math.min(px + fov, width - 1);
  const stopY = Math.min(py + fov, height - 1);
  const fovWidth = stopX - startX + 1;
  const fovHeight = stopY - startY + 1;
  const playerPoint = { x: px, y: py };
  const limite = fov * fov;

  function isVisible(posPlayer, pos, dungeon, player) {
    return getSegment(posPlayer, pos).reduce(function (a, { x, y }) {
      const p = x + y * width;

      return (
        a &&
        (isEmptyPosition({ dungeon, player }, p) ||
          (x === pos.x && y === pos.y))
      );
    }, true);
  }

  const viewed = new Array(fovWidth * fovHeight)
    .fill(-1)
    .reduce(function (a, _, i) {
      const ix = startX + (i % fovWidth);
      const iy = startY + Math.trunc(i / fovWidth);
      const point = { x: ix, y: iy };
      const dist = distanceEucl(playerPoint, point);
      if (dist <= limite && isVisible(playerPoint, point, dungeon, player)) {
        return [...a, ix + iy * width];
      }
      return a;
    }, []);
  return viewed;
}

export default getVisibles;
