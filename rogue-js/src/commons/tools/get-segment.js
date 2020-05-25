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
        points: [...points, [x, y]],
      })
    : [...points, [x, y]];
};

const reverse = (points) => points.map(([x, y]) => [y, x]);

export function getSegment([ax, ay], [bx, by]) {
  const dx = bx - ax;
  const dy = by - ay;
  const xinc = dx > 0 ? 1 : -1;
  const yinc = dy > 0 ? 1 : -1;
  if (Math.abs(dx) > Math.abs(dy)) {
    return deltaX({
      x: ax,
      dx: Math.abs(dx),
      xinc,
      y: ay,
      dy: Math.abs(dy),
      yinc,
      count: Math.abs(dx),
      cumul: Math.abs(dx / 2),
    });
  }
  return reverse(
    deltaX({
      x: ay,
      count: Math.abs(dy),
      dx: Math.abs(dy),
      xinc: yinc,
      y: ax,
      dy: Math.abs(dx),
      yinc: xinc,
      cumul: Math.abs(dy / 2),
    })
  );
}
