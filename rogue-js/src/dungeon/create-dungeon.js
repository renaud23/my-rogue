import createCave from "./cave";
import { TILES, peekOne } from "../commons";

function getStairsUp(cave) {
  return { tile: TILES.stairsUp, position: peekOne(cave.emptyTiles) };
}

function getStairsDown(cave) {
  return { tile: TILES.stairsDown, position: peekOne(cave.emptyTiles) };
}

function createCaves(nb, width, height) {
  return new Array(nb)
    .fill({})
    .map(function (_, i) {
      const cave = createCave(width, height);
      const stairs = {};
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
      return [...a, cave];
    }, []);
}

function createDungeon(nb = 10, width = 30, height = 30) {
  const levels = createCaves(nb, width, height);
  return {
    getWidth: (current) => levels[current].width,
    getHeight: (current) => levels[current].height,
    getData: (current) => levels[current].data,
    getStairs: (current) => levels[current].stairs,
    getEmptyTiles: (current) => levels[current].emptyTiles,
    peekEmptyTile: (current) => peekOne(levels[current].emptyTiles),
  };
}

export default createDungeon;
