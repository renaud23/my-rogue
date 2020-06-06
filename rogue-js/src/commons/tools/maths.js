export const distanceEucl = (a, b) =>
  Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2);

export function distanceEucl2([x1, y1], [x2, y2]) {
  const xx = x2 - x1;
  const yy = y2 - y1;
  return Math.pow(xx, 2) + Math.pow(yy, 2);
}

export function antecedentPoint(position, width) {
  return [position % width, Math.trunc(position / width)];
}

export function pointProjection([x, y], width) {
  return x + width * y;
}

export function computeDistance(posA, posB, width) {
  return distanceEucl2(
    antecedentPoint(posA, width),
    antecedentPoint(posB, width)
  );
}

export const maxMin = (value, max, min) => Math.min(Math.max(value, max), min);

export const randomInt = (limite) =>
  Math.floor(Math.random() * Math.floor(limite));
