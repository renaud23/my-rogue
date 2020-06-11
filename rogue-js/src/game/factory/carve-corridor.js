import { randomInt, distanceEucl2 } from "../../commons";

function getMiddle(rect) {
  const [x, y, w, h] = rect;
  return [Math.trunc(x + w / 2), Math.trunc(y + h / 2)];
}

function findNearest(lefts, rights) {
  const [nearest] = lefts.reduce(
    function ([pair, dist], zl) {
      const [zr, newDist] = findNearestRoom(zl, rights);
      if (dist > newDist) {
        return [[zl, zr], newDist];
      }

      return [pair, dist];
    },
    [[undefined, undefined], 9999999]
  );

  return nearest;
}

function walkTo(rect, room, from, to) {
  const [fx, fy] = from;
  const [tx, ty] = to;
  const [x, y, width] = rect;
  const dx = tx - fx;
  const dy = ty - fy;

  const [nx, ny] =
    Math.abs(dx) > Math.abs(dy)
      ? [fx + dx / Math.abs(dx), fy]
      : [fx, fy + dy / Math.abs(dy)];

  if (ny === ty && nx === tx) {
    return room;
  }

  const nextRoom = [...room];
  nextRoom[nx - x + (ny - y) * width] = 0;
  return walkTo(rect, nextRoom, [nx, ny], to);
}

function findNearestRoom(rect, candidats) {
  const ca = getMiddle(rect);

  return candidats.reduce(
    function ([best, dist], candidat) {
      const cb = getMiddle(candidat);
      const newDist = distanceEucl2(ca, cb);
      if (newDist < dist) {
        return [candidat, newDist];
      }
      return [best, dist];
    },
    [undefined, 9999]
  );
}

function carveCorridor(rect, room, left, right) {
  const [rectLeft, [roomLeft, zonesLeft]] = left;
  const [rectRight, [roomRight, zonesRight]] = right;
  const [one, two] = findNearest(zonesLeft, zonesRight);
  const c1 = getMiddle(one);
  const c2 = getMiddle(two);

  return walkTo(rect, room, c1, c2);
}

export default carveCorridor;
