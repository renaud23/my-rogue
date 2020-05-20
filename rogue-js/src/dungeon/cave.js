import joinAll from "./cave-join-all";

const EMPTY = 0;
const ROCK = 1;

const fillWithRock = (width, height, data = []) =>
  data.length < width * height
    ? fillWithRock(width, height, [...data, ROCK])
    : { data, width, height };

const nbNeigbourgEmpty = (data, width) => (i, j) => {
  let nb = 0;
  nb += data[i * width + j - 1] === EMPTY ? 1 : 0;
  nb += data[i * width + j + 1] === EMPTY ? 1 : 0;
  nb += data[(i + 1) * width + j] === EMPTY ? 1 : 0;
  nb += data[(i - 1) * width + j] === EMPTY ? 1 : 0;
  nb += data[(i + 1) * width + j + 1] === EMPTY ? 1 : 0;
  nb += data[(i - 1) * width + j - 1] === EMPTY ? 1 : 0;
  nb += data[(i + 1) * width + j - 1] === EMPTY ? 1 : 0;
  nb += data[(i - 1) * width + j + 1] === EMPTY ? 1 : 0;

  return nb;
};

const visite = (data, width) => (i, j) => {
  const value = data[i * width + j];
  const nb = nbNeigbourgEmpty(data, width)(i, j);
  if (value === ROCK) {
    if (nb > 6) return ROCK;
    return EMPTY;
  }
  if (nb < 4) return EMPTY;
  return ROCK;
};

const visiteAll = ({ data, width, height }) => ({
  data: data.map((d, i) => {
    const row = Math.trunc(i / width);
    const col = i % width;
    return row !== 0 && col !== 0 && row !== height - 1 && col !== width - 1
      ? visite(data, width)(row, col)
      : ROCK;
  }),
  width,
  height,
});

const randomise = ({ data, width, height }) => ({
  data: data.map((j) =>
    Math.floor(Math.random() * Math.floor(100)) > 45 ? EMPTY : j
  ),
  width,
  height,
});

/* */
const findEmptyTiles = (data) =>
  data.reduce((a, tile, i) => (tile === EMPTY ? [...a, i] : a), []);

const carve = ({ data = [], width, height }, i = 1) =>
  i > 0
    ? carve(visiteAll({ data, width, height }), i - 1)
    : { data, width, height };

const createCave = function (width, height) {
  const step1 = carve(randomise(fillWithRock(width, height)), 15);
  const step2 = joinAll(step1);

  return {
    width,
    height,
    tiles: { empty: EMPTY, rock: ROCK },
    data: step2.data,
    emptyTiles: findEmptyTiles(step2.data),
  };
};
export default createCave;
