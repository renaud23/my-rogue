import { antecedentPoint, distanceEucl2, getSegment } from "../../commons";
import { isVisiblePosition } from "../commons";

function isVisibleSegment(state, segment) {
  const { dungeon, player } = state;
  const { currentLevel } = player;
  const dw = dungeon.getWidth(currentLevel);
  return segment.reduce(function (a, [x, y], i) {
    if (i === 0 || i === segment.length - 1) {
      return a;
    }
    const pos = x + y * dw;
    return a && isVisiblePosition(state, currentLevel, pos);
  }, true);
}

function getVisibles(state) {
  const { player, dungeon } = state;
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
  const playerPoint = [px, py];
  const limite = fov * fov;

  const visibles = new Array(fovWidth * fovHeight)
    .fill(null)
    .reduce(function (a, _, i) {
      const [ix, iy] = antecedentPoint(i, fovWidth);
      const x = ix + startX;
      const y = iy + startY;
      const candidat = [x, y];
      const distance = distanceEucl2(playerPoint, candidat);

      if (distance <= limite) {
        const segment = getSegment(playerPoint, [x, y]);
        if (isVisibleSegment(state, segment)) {
          return [...a, startX + ix + (startY + iy) * width];
        }
      }

      return a;
    }, []);

  return visibles;
}

export default getVisibles;
