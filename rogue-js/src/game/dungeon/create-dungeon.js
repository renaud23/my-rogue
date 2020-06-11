import createCave from "./cave";
import createFactory from "./factory";
import { TILES, popOne, peekOne } from "../../commons";
import { createRandomSimple } from "../objects";

function getStairsUp(cave) {
  return { tile: TILES.stairsUp, position: popOne(cave.emptyTiles) };
}

function getStairsDown(cave) {
  return { tile: TILES.stairsDown, position: popOne(cave.emptyTiles) };
}

function fillObject(cave) {
  const simples = new Array(5).fill(null).map(function () {
    return { ...createRandomSimple(), position: peekOne(cave.emptyTiles) };
  });

  return { ...cave, objects: [...simples] };
}

function createLevel(num, width, height) {
  if (num % 2 === 1) {
    return createCave(width, height);
  } else {
    return createFactory(width, height);
  }
}

function createCaves(nb, width, height) {
  return new Array(nb)
    .fill({})
    .map(function (_, i) {
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
    })
    .reduce(function (a, cave) {
      const { stairs } = cave;
      Object.values(stairs).forEach(
        ({ tile, position }) => (cave.data[position] = tile.code)
      );
      return [...a, fillObject(cave)];
    }, []);
}

function createDungeon(nb = 10, width = 30, height = 30) {
  const levels = createCaves(nb, width, height);
  return {
    getWidth: (current) => levels[current].width,
    getHeight: (current) => levels[current].height,
    getData: (current) => levels[current].data,
    getStairs: (current) => levels[current].stairs,
    getEmptyTiles: (current) => [...levels[current].emptyTiles],
    peekEmptyTile: (current) => popOne(levels[current].emptyTiles),
    getDungeonHeight: () => nb,
  };
}

export default createDungeon;
