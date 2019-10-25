import { getSegment, maxMin, distanceEucl } from "../commons";
import { isAtPos, getTileAt } from "../ennemy";

const isVisibleforPlayer = game => pos => {
  const { player, dungeon } = game;
  const { position, fov } = player;
  const { width, data, tiles } = dungeon;
  const x = position % width;
  const y = Math.trunc(position / width);
  const fov2 = Math.pow(fov, 2);

  if (distanceEucl(pos, { x, y }) > fov2) {
    return false;
  }
  const [first, ...segment] = getSegment({ x, y }, pos);
  const is = segment.reduce(
    (a, v, i) =>
      a && (i === segment.length - 1 || data[v.x + v.y * width] !== tiles.rock),
    true
  );

  return is;
};

const getVisibleEnnemy = game => pos => {
  const { ennemies } = game;
  return ennemies.reduce((a, e) => (isAtPos(e)(pos) ? e : a), undefined);
};

export const reduceFOV = game => {
  const { dungeon, player } = game;
  if (!dungeon) return;
  const { width, data, height } = dungeon;
  const { fov, worldView } = player;

  const wFov = fov * 2 + 1;
  const startX = maxMin((player.position % width) - fov, 0, width - wFov);
  const startY = maxMin(
    Math.trunc(player.position / width) - fov,
    0,
    height - wFov
  ); //
  // console.log(startX, player.position % width, width - wFov); //

  const whatsNew = new Array(Math.pow(wFov, 2)).fill(null).reduce(
    (a, o, i) => {
      const { visibleTiles, memorisedTiles, visibleEnnemies } = a;
      const xi = i % wFov;
      const yi = Math.trunc(i / wFov);
      const inGamePos = startX + xi + (startY + yi) * width;

      const ennemy = getVisibleEnnemy(game)(inGamePos);
      const ve = ennemy
        ? { ...visibleEnnemies, [inGamePos]: getTileAt(ennemy)(inGamePos) }
        : visibleEnnemies;

      return isVisibleforPlayer(game)({ x: startX + xi, y: startY + yi })
        ? {
            ...a,
            memorisedTiles: {
              ...memorisedTiles,
              [inGamePos]: data[inGamePos]
            },
            visibleTiles: { ...visibleTiles, [inGamePos]: data[inGamePos] },
            visibleEnnemies: ve
          }
        : a;
    },
    { ...worldView, visibleTiles: {}, visibleEnnemies: {} }
  );

  return { ...player, worldView: whatsNew };
};

export default () => ({
  worldView: {
    visibleTiles: {},
    memorisedTiles: {},
    visibleEnnemies: {}
  },
  position: undefined, //
  fov: 12
});
