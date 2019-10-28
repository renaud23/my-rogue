export const distanceEucl = (a, b) =>
  Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2);

export const maxMin = (value, max, min) => Math.min(Math.max(value, max), min);

export const randomInt = limite =>
  Math.floor(Math.random() * Math.floor(limite));
