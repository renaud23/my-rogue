import { distanceEucl, getSegment } from "../commons/tools";

function isEmpty({ dungeon }, pos) {
  const { data, tiles } = dungeon;
  return data[pos] === tiles.empty;
}

function getVisibles({ player, dungeon }) {
  const { fov, position } = player;
  const { data, width, height } = dungeon;

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

  function isVisible(posPlayer, pos, dungeon) {
    const { width } = dungeon;

    return getSegment(posPlayer, pos).reduce(function (a, { x, y }) {
      const p = x + y * width;

      return a && (isEmpty({ dungeon }, p) || (x === pos.x && y === pos.y));
    }, true);
  }

  const viewed = new Array(fovWidth * fovHeight)
    .fill(-1)
    .reduce(function (a, _, i) {
      const ix = startX + (i % fovWidth);
      const iy = startY + Math.trunc(i / fovWidth);
      const point = { x: ix, y: iy };
      const dist = distanceEucl(playerPoint, point);
      if (dist <= limite && isVisible(playerPoint, point, dungeon)) {
        return [...a, ix + iy * width];
      }
      return a;
    }, []);

  return viewed;
}

export default getVisibles;
