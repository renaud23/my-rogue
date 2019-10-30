import { maxMin } from "../game/common-tools";

const getTile = (code, TILES) => {
  switch (code) {
    case TILES.rock:
      return "X";

    default:
      return " ";
  }
};

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

export default () => ({ canvasMap }) => game => {
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
  //
  const limX = Math.min(50, ww);
  const limY = Math.min(50, wh);
  // const mx =
  //   limX === ww ? minx : maxMin(px - Math.trunc(limX / 2), 0, width - limX);
  // const my =
  //   limY === wh ? miny : maxMin(py - Math.trunc(limY / 2), 0, height - limY);

  // const data = Object.entries(memorisedTiles).reduce((a, [pos, value]) => {
  //   const x = pos % width;
  //   const y = Math.trunc(pos / width); //
  //   if (x >= mx && x <= limX + mx && y >= my && y <= limY + my) {
  //     a[x - mx + (y - my) * limX] = getTile(value, tiles);
  //   }
  //   return a;
  // }, new Array(limX * limY).fill("."));
  // data[px - mx + (py - my) * limX] = "O";

  // const content = data.reduce(
  //   (a, code, i) => ((i + 1) % limX === 0 ? `${a}${code}\r\n` : `${a}${code}`),
  //   ""
  // );
};
