import createOffscreen, { createTexture } from "./rendering";
import { maxMin } from "../game/common-tools";

const getSize = width => pos =>
  pos.reduce(
    ({ ww, wh, minx, maxx, maxy, miny }, pos) => {
      const x = pos % width;
      const y = Math.trunc(pos / width);

      const nmaxx = Math.max(maxx, x);
      const nminx = Math.min(minx, x);
      const nmaxy = Math.max(maxy, y);
      const nminy = Math.min(miny, y);

      return {
        ww: nmaxx - nminx + 1,
        wh: nmaxy - nminy + 1,
        minx: nminx,
        maxx: nmaxx,
        maxy: nmaxy,
        miny: nminy
      };
    },
    { ww: 0, wh: 0, minx: 99999, maxx: -1, maxy: -1, miny: 99999 }
  );

export default ({ canvasMap, map }) => {
  const offscreen = createOffscreen(canvasMap, 100, 100);
  const texture = createTexture(`${window.location.origin}/texture.png`);
  const mapRenderer = createMapRenderer(map);
  return game => {
    mapRenderer({ offscreen, texture })(game);
    offscreen.render();
  };
};

const createMapRenderer = ({
  size = 4,
  width: mapWidth = 30,
  height: mapHeight = 30
} = {}) => ({ offscreen, texture }) => game => {
  const { dungeon, player } = game;
  if (!dungeon || !player) return null;
  const { width, tiles, height } = dungeon;
  const {
    worldView: { memorisedTiles },
    position
  } = player;

  const px = position % width;
  const py = Math.trunc(position / width);
  const { ww, wh, minx, miny } = getSize(width)(Object.keys(memorisedTiles));

  const limX = Math.min(mapWidth, ww);
  const limY = Math.min(mapHeight, wh);

  offscreen.resize(limX * size, limY * size);
  offscreen.clear();

  const mx =
    limX === ww ? minx : maxMin(px - Math.trunc(limX / 2), 0, width - limX);
  const my =
    limY === wh ? miny : maxMin(py - Math.trunc(limY / 2), 0, height - limY);

  const { drTiles } = Object.entries(memorisedTiles).reduce(
    (a, [pos, value]) => {
      const x = pos % width;
      const y = Math.trunc(pos / width);
      if (x >= mx && x <= limX + mx && y >= my && y <= limY + my) {
        if (value === tiles.empty) {
          a.drTiles.push({ x: x - mx, y: y - my, tx: 0, ty: 0 });
        } else if (value === tiles.rock) {
          a.drTiles.push({ x: x - mx, y: y - my, tx: 16, ty: 0 });
        }
      }
      return a;
    },
    { drTiles: [] }
  );
  drTiles.forEach(({ x, y, tx, ty }) => {
    offscreen.drawTexture(
      texture,
      tx,
      ty,
      16,
      16,
      x * size,
      y * size,
      size,
      size
    );
  });

  offscreen.fillRect(
    "#ff00ff",

    (px - mx) * size,
    (py - my) * size,
    size,
    size
  );
};
