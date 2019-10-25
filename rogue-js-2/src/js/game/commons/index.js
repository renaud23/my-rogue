export * from "./is-empty-position";
export * from "./chrono";

const deltaY = ({ dx, y, dy, yinc, cumul }) => {
  return cumul + dy >= dx
    ? { cumul: cumul + dy - dx, y: y + yinc }
    : { cumul: cumul + dy, y };
};

const deltaX = ({ x, dx, xinc, y, dy, yinc, cumul, points = [], count }) => {
  return count > 0
    ? deltaX({
        ...deltaY({ dx, y, dy, yinc, cumul }),
        count: count - 1,
        x: x + xinc,
        dx,
        xinc,
        dy,
        yinc,
        points: [...points, { x, y }]
      })
    : [...points, { x, y }];
};

const reverse = points => points.map(({ x, y }) => ({ y: x, x: y }));

export const getSegment = (a, b) => {
  const dx = b.x - a.x;
  const dy = b.y - a.y;
  const xinc = dx > 0 ? 1 : -1;
  const yinc = dy > 0 ? 1 : -1;

  if (Math.abs(dx) > Math.abs(dy)) {
    return deltaX({
      x: a.x,
      dx: Math.abs(dx),
      xinc,
      y: a.y,
      dy: Math.abs(dy),
      yinc,
      count: Math.abs(dx),
      cumul: Math.abs(dx / 2)
    });
  }

  return reverse(
    deltaX({
      x: a.y,
      count: Math.abs(dy),
      dx: Math.abs(dy),
      xinc: yinc,
      y: a.x,
      dy: Math.abs(dx),
      yinc: xinc,
      cumul: Math.abs(dy / 2)
    })
  );
};

export const posToCoord = width => pos => ({
  x: pos % width,
  y: Math.trunc(pos / width)
});

export const distanceEucl = (a, b) =>
  Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2);

export const maxMin = (value, max, min) => Math.min(Math.max(value, max), min);

export const randomInt = limite =>
  Math.floor(Math.random() * Math.floor(limite));

/*
 *
 */
export const getEmptyTilesPosition = (game = {}) => {
  const { dungeon, player } = game;
  if (!dungeon || player) return [];
  return [...dungeon.emptyTiles].filter(p => p !== player.position);
};

export const canSee = game => (a, b) => {
  const { dungeon } = game;
  const { data, width, tiles } = dungeon;

  const [first, ...segment] = getSegment(
    { x: a % width, y: Math.trunc(a / width) },
    { x: b % width, y: Math.trunc(b / width) }
  );
  return segment.reduce(
    (a, v, i) =>
      a && (i === segment.length - 1 || data[v.x + v.y * width] !== tiles.rock),
    true
  );
};
