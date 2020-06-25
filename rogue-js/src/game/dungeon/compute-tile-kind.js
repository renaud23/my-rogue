import { getWallCode } from "../commons/dungeon-tiles";

const DOORS_CODES = [0b000101101, 0b101101000, 0b110000110, 0b011000011];

function mergeKinds(kinds, key, pos) {
  return key in kinds
    ? { ...kinds, [key]: [...kinds[key], pos] }
    : { ...kinds, [key]: [pos] };
}

function buildWalls(kinds) {
  const { walls } = kinds;
  const next = walls.reduce(function (a, [pos, val]) {
    return { ...a, [pos]: val };
  }, {});

  return { ...kinds, walls: next };
}

function compute(dungeon) {
  const { width, height, data } = dungeon;
  const kinds = data.reduce(
    function (a, tile, i) {
      const x = i % width;
      const y = Math.trunc(i / width);
      if (x === 0 || y === 0 || x === width - 1 || y === height - 1) {
        return mergeKinds(a, "border-map", i);
      }

      const tileValue = new Array(9).fill(0).reduce(function (value, _, j) {
        const xi = j % 3;
        const yi = Math.trunc(j / 3);
        const pi = i + xi - 1 + (yi - 1) * width;

        if (data[pi] === 1) {
          return value + Math.pow(2, 8 - j);
        }

        return value;
      }, 0);

      const wallCode = getWallCode(tileValue);
      if (wallCode) {
        return mergeKinds(a, "walls", [i, wallCode]);
      }

      if (DOORS_CODES.indexOf(tileValue) !== -1) {
        return mergeKinds(a, "doors", i);
      }

      return a;
    },
    { doors: [], walls: [] }
  );

  return { ...dungeon, ...buildWalls(kinds) };
}

export default compute;
