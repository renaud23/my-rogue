import createCave from "./cave";
import { TILES, peekOne } from "../commons";

function getStairsTile(level, dungeonSize) {
  if (level === 0) {
    return TILES.stairsUp;
  }
  if (level === dungeonSize - 1) {
    return TILES.stairsDown;
  }

  return TILES.stairsUpDown;
}

function createCaves(nb, width, height) {
  return new Array(nb).fill({}).map(function (_, i) {
    const cave = createCave(width, height);
    if (nb === 1) return cave;
    const position = peekOne(cave.emptyTiles);
    const tile = getStairsTile(i, nb);
    cave.data[position] = tile.code;

    return { ...cave, stairs: { position, tile } };
  });
}

function createDungeon(nb = 10, width = 30, height = 30) {
  const levels = createCaves(nb, width, height);
  return {
    getWidth: (current) => levels[current].width,
    getHeight: (current) => levels[current].height,
    getData: (current) => levels[current].data,
    getEmptyTiles: (current) => levels[current].emptyTiles,
    peekEmptyTile: (current) => peekOne(levels[current].emptyTiles),
  };
}

export default createDungeon;
