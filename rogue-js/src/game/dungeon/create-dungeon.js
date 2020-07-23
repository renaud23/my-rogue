import { findRegions, getCoords } from "./dungeon-maze";
import createDungeonWithMaze from "./dungeon-maze";
import { TILES } from "../../commons";
import { computeWallCode } from "./compute-tile-kind";
import { affectDungeonBiome, BIOMES } from "./dungeon-biomes";

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

function print(state) {
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

function getStairsUp(position) {
  return { tile: TILES.stairsUp, position };
}

function getStairsDown(position) {
  return { tile: TILES.stairsDown, position };
}

function createTilesInfo(level) {
  const { data, regions } = level;
  const { zones } = regions;
  const wallCodes = computeWallCode(level);
  const biomeCodes = zones.reduce(function (a, zone) {
    const { positions, biome } = zone;
    return a.map(function (value, i) {
      return positions.indexOf(i) !== -1 ? biome : value;
    });
  }, new Array(data.length).fill(-1));
  const tilesInfo = data.map(function (value, i) {
    return { value, wallCode: wallCodes[i], biome: biomeCodes[i] };
  });

  return { ...level, tilesInfo };
}

function createDungeonTilesInfo(dungeon) {
  return dungeon.map(function (level) {
    return createTilesInfo(level);
  });
}

function createLevel(width, height) {
  return createTilesInfo(findRegions(createDungeonWithMaze(width, height)));
}

function diffRandomPos(level, pos) {
  const { regions } = level;
  const { zones } = regions;
  const lastRegion = zones[zones.length - 1];
  const { positions } = lastRegion;
  const newPos = positions[positions.length - 1];
  if (newPos !== pos) {
    return newPos;
  }
  return diffRandomPos(level, pos);
}

function createFirstFloor(width, height) {
  const level = createLevel(width, height);
  const { regions } = level;
  const { start } = regions;
  const stairs = { up: getStairsUp(diffRandomPos(level, start)) };
  return { ...level, stairs };
}

function createFloor(width, height) {
  const level = createLevel(width, height);
  const { regions } = level;
  const { start } = regions;
  const stairs = {
    up: getStairsUp(diffRandomPos(level, start)),
    down: getStairsDown(start),
  };
  return { ...level, stairs };
}

function createLastFloor(width, height) {
  const level = createLevel(width, height);
  const { regions } = level;
  const { start } = regions;
  const stairs = { down: getStairsDown(start) };
  return { ...level, stairs };
}

function createCaves(nb, width, height) {
  return new Array(nb).fill({}).map(function (_, i) {
    if (i === 0) {
      return createFirstFloor(width, height);
    }

    if (i === nb - 1) {
      return createLastFloor(width, height);
    }

    return createFloor(width, height);
  });
}

const getEmptyTiles = (levels) => (level) => {
  if (level !== undefined) {
    return [...levels[level].empties];
  }
  return levels.map(function (level) {
    return [...level.empties];
  });
};

const getWalls = (levels) => (level) => {
  return levels[level].wallCodes || {};
};

function createDungeon(nb = 10, width = 30, height = 30) {
  const levels = createDungeonTilesInfo(
    affectDungeonBiome(createCaves(nb, width, height))
  );

  return {
    getStartPos: () => levels[0].regions.start,
    getRegions: (level) => levels[level].regions,
    getLevel: (level) => levels[level],
    getWidth: (current) => levels[current].width,
    getHeight: (current) => levels[current].height,
    getData: (current) => levels[current].data,
    getStairs: (current) => levels[current].stairs,
    getDoors: (current) => {
      return levels[current].doors || [];
    },
    getEmptyTiles: getEmptyTiles(levels),
    getLevels: () => levels,
    getTilesInfo: (level) => levels[level].tilesInfo,
    getDungeonHeight: () => nb,
  };
}

export default createDungeon;
