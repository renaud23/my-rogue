import createCave from "./cave";
import createFactory from "./factory";
import { TILES } from "../../commons";
import computeTileKind from "./compute-tile-kind";
import { randomInt } from "../../commons";

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

function createLevel(num, width, height) {
  if (num % 2 === 1) {
    return createCave(width, height);
  } else {
    return computeTileKind(createFactory(width, height));
  }
}

function createCaves(nb, width, height) {
  return new Array(nb).fill({}).map(function (_, i) {
    const cave = createLevel(i, width, height);

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

function createDungeon(nb = 10, width = 30, height = 30) {
  const levels = createCaves(nb, width, height);
  return {
    getWidth: (current) => levels[current].width,
    getHeight: (current) => levels[current].height,
    getData: (current) => levels[current].data,
    getStairs: (current) => levels[current].stairs,
    getDoors: (current) => levels[current].doors || [],
    getEmptyTiles: getEmptyTiles(levels),
    // peekEmptyTile: (current) => popOne(levels[current].emptyTiles),
    getDungeonHeight: () => nb,
  };
}

export default createDungeon;
