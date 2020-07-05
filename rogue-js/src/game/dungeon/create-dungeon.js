import createDungeonWithMaze from "./dungeon-maze";
import { TILES } from "../../commons";
import computeTileKind from "./compute-tile-kind";
import computeEmptyTiles from "./compute-empty-tiles";
import { randomInt } from "../../commons";

function getVal(pos, state) {
  const { emptyTiles, doors } = state;

  if (doors.indexOf(pos) !== -1) {
    return "D";
  }
  if (emptyTiles.indexOf(pos) !== -1) {
    return ".";
  }
  return "X";
}

export function print(state) {
  const { data, width, doors } = state;
  const [rows] = data.reduce(
    function ([rows, current], code, i) {
      if (i % width === width - 1) {
        return [
          [...rows, `${current}${getVal(i, state)} ${Math.trunc(i / width)}`],
          "",
        ];
      }

      return [rows, `${current}${getVal(i, state)}`];
    },
    [[], ""]
  );
  console.log({ doors });
  rows.forEach(function (row) {
    console.log(row);
  });
}

function popOne(cave) {
  const { emptyTiles } = cave;
  return emptyTiles.splice(randomInt(emptyTiles.length), 1)[0];
}

function getStairsUp(cave) {
  return { tile: TILES.stairsUp, position: popOne(cave) };
}

function getStairsDown(cave) {
  return { tile: TILES.stairsDown, position: popOne(cave) };
}

function createLevel(width, height) {
  const level = computeEmptyTiles(createDungeonWithMaze(width, height));
  return computeTileKind(level);
}

function createCaves(nb, width, height) {
  return new Array(nb).fill({}).map(function (_, i) {
    const cave = createLevel(width, height);

    if (i === 0) {
      return { ...cave, stairs: { up: getStairsUp(cave) } };
    }

    if (i === nb - 1) {
      return { ...cave, stairs: { down: getStairsDown(cave) } };
    }

    return {
      ...cave,
      stairs: { up: getStairsUp(cave), down: getStairsDown(cave) },
    };
  });
}

const getEmptyTiles = (levels) => (level) => {
  if (level !== undefined) {
    return [...levels[level].emptyTiles];
  }
  return levels.map(function (level) {
    return [...level.emptyTiles];
  });
};

const getWalls = (levels) => (level) => {
  return levels[level].wallCodes || {};
};

function createDungeon(nb = 10, width = 30, height = 30) {
  const levels = createCaves(nb, width, height);
  return {
    getWidth: (current) => levels[current].width,
    getHeight: (current) => levels[current].height,
    getData: (current) => levels[current].data,
    getStairs: (current) => levels[current].stairs,
    getDoors: (current) => {
      return levels[current].doors || [];
    },
    getEmptyTiles: getEmptyTiles(levels),
    getWallCodes: getWalls(levels),
    getDungeonHeight: () => nb,
  };
}

export default createDungeon;
