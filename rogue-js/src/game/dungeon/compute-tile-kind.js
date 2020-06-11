const MATRICE = [1, 2, 4, 8, 0, 16, 32, 64, 128];

function mergeKinds(kinds, key, pos) {
  return key in kinds
    ? { ...kinds, [key]: [...kinds[key], pos] }
    : { ...kinds, [key]: [pos] };
}

function compute(dungeon) {
  const { width, data } = dungeon;
  const kinds = data.reduce(function (a, tile, i) {
    if (tile === 1) {
      return mergeKinds(a, "wall", i);
    }

    const tileValue = new Array(9).fill(0).reduce(function (value, _, j) {
      if (tile === 0) {
        const xi = j % 3;
        const yi = Math.trunc(j / 3);
        const pi = i + xi - 1 + (yi - 1) * width;
        if (data[pi] === 0) {
          return value + MATRICE[xi + yi * 3];
        }
      }

      return value;
    }, 0);

    if (
      tileValue === 71 ||
      tileValue === 57 ||
      tileValue === 226 ||
      tileValue === 156
    ) {
      return mergeKinds(a, "doors", i);
    }

    return a;
  }, {});

  return { ...dungeon, ...kinds };
}

export default compute;
