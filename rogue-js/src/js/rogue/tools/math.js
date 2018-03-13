export const getDistance = (ax, ay, bx, by) => {
  let dx = ax - bx;
  dx *= dx;
  let dy = ay - by;
  dy *= dy;
  return dx + dy;
};

export const getCircle = (cx, cy, rayon) => {
  const points = [];

  let x, y, d;
  x = 0;
  y = rayon;
  d = 5 - 4 * rayon;
  while (x <= y) {
    points.push({ x: x + cx, y: y + cy });
    points.push({ x: y + cx, y: x + cy });
    points.push({ x: -x + cx, y: y + cy });
    points.push({ x: y + cx, y: -x + cy });

    points.push({ x: x + cx, y: -y + cy });
    points.push({ x: -y + cx, y: x + cy });
    points.push({ x: -x + cx, y: -y + cy });
    points.push({ x: -y + cx, y: -x + cy });
    if (d > 0) {
      y--;
      d = d - 8 * y;
    }
    x++;
    d = d + 8 * x + 4;
  }

  return points;
};

export const isInCicle = (cx, cy, rayon, x, y) => {
  let dx = x - cx;
  dx *= dx;
  let dy = y - cy;
  dy *= dy;
  return dx + dy <= rayon * rayon;
};

export const getSegment = (ax, ay, bx, by) => {
  const points = [];
  let dx, dy, i, xinc, yinc, cumul, x, y;
  x = ax;
  y = ay;
  dx = bx - ax;
  dy = by - ay;
  xinc = dx > 0 ? 1 : -1;
  yinc = dy > 0 ? 1 : -1;
  dx = Math.abs(dx);
  dy = Math.abs(dy);

  points.push({ x, y });

  if (dx > dy) {
    cumul = Math.trunc(dx / 2);
    for (i = 1; i <= dx; i++) {
      x += xinc;
      cumul += dy;
      if (cumul >= dx) {
        cumul -= dx;
        y += yinc;
      }
      points.push({ x, y });
    }
  } else {
    cumul = dy / 2;
    for (i = 1; i <= dy; i++) {
      y += yinc;
      cumul += dx;
      if (cumul >= dy) {
        cumul -= dy;
        x += xinc;
      }
      points.push({ x, y });
    }
  }

  return points;
};
