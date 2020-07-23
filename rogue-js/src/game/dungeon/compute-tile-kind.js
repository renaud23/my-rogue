import { getWallCode } from "../commons/dungeon-tiles";
import { getNeighbors, TILES, isInBound } from "./dungeon-maze/common";
import carveFullWall from "./dungeon-maze/carve-full-wall";

// isInBound(position, width, height, limite = 0)

// function mergeKinds(kinds, key, pos) {
//   return key in kinds
//     ? { ...kinds, [key]: [...kinds[key], pos] }
//     : { ...kinds, [key]: [pos] };
// }

// function buildWalls(kinds) {
//   const { walls } = kinds;
//   const next = walls.reduce(function (a, [pos, val]) {
//     return { ...a, [pos]: val };
//   }, {});

//   return { ...kinds, walls: next };
// }

// function compute(dungeon) {
//   const { width, height, data } = dungeon;
//   const kinds = data.reduce(
//     function (a, tile, i) {
//       const x = i % width;
//       const y = Math.trunc(i / width);
//       if (x === 0 || y === 0 || x === width - 1 || y === height - 1) {
//         return mergeKinds(a, "border-map", i);
//       }

//       const tileValue = new Array(9).fill(0).reduce(function (value, _, j) {
//         const xi = j % 3;
//         const yi = Math.trunc(j / 3);
//         const pi = i + xi - 1 + (yi - 1) * width;

//         if (data[pi] === 1) {
//           return value + Math.pow(2, 8 - j);
//         }

//         return value;
//       }, 0);

//       const wallCode = getWallCode(tileValue);
//       if (wallCode) {
//         return mergeKinds(a, "walls", [i, wallCode]);
//       }

//       return a;
//     },
//     { doors: [], walls: [] }
//   );

//   return { ...dungeon, ...buildWalls(kinds) };
// }
// N 8 S 4 W 2 E 1

export function computeWallCode(level) {
  const { data, width, height } = level;
  return data.map(function (a, i) {
    if (isInBound(i, width, height)) {
      if (a === 0) {
        return a;
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

      return tileValue;
      // const wallCode = getWallCode(tileValue);
      // if (wallCode) {
      //   return tileValue;
      // }
    }
    return a;
  });
}

function compute(level) {
  const wallCodes = computeWallCode(level);

  return { ...level, wallCodes };
}

export default compute;
